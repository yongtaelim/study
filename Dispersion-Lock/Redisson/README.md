# Redisson

참조 : https://hyperconnect.github.io/2019/11/15/redis-distributed-lock-1.html

## 개요

분산된 서버들간에 동기화를 위한 락(lock)을 처리하는 것

## 간단한 분산 락
### 구현

일반적인 로컬 스핀 락을 구현하는 것과 유사하게 분산락을 구현하였습니다.

```
void doProcess() {
  String lockKey = "lock";
  
  try {
    while (!tryLock(lockKey)) {   // 2
      try {
        Thread.sleep(50);        
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    // do process 3
  } finally {
    unlock(lockKey);    // 4
  }
}

boolean tryLock(String key) {
  return command.setnx(key, "1");   // 1
}

void unlock(String key) {
  command.del(key);
}
```

1. 락을 획득한다는 것은 “락이 존재하는지 확인”, “존재하지 않는다면 락을 획득” 두 연산이 atomic하게 이루어져야한다. Redis는 “값이 존재하지 않으면 세팅” 이라는 setnx 명령어를 지원한다. 이 setnx를 이용하여 Redis에 값이 존재하지 않으면 세팅하게 되고, 값이 세팅되었는지 여부를 Return 값으로 받아 락을 획득하는데 성공 여부 확인한다.
2. try 구문 안에서 Lock을 획득할때까지 계속 Lock 획득을 시도한다. 그리고 Redis에 너무 많은 요청이 가지 않도록 약간의 sleep을 걸어준다.
3. Lock을 획득한 후에 연산을 수행한다.
4. Lock을 사용 후에는 꼭 해제하도록 finally 에서 Lock을 해제한다.

### 분산 Lock 개발시 나타날 수 있는 문제점과 해결 방법

- Lock의 타임아웃이 지정되어 있지 않음
  - 위 코드 처럼 스핀 락을 구현했을 시에 락을 획득하지 못하면 무한 루프에 빠진다. 특정한 어플리케이션에서 tryLock을 성공했는데 오류가 발생하여 어플리케이션이 종료된다면 다른 어플리케이션은 영원히 락을 획득하지 못하는 문제가 발생한다. 

- tryLock 로직은 try-finally 구문 밖에서 수행해야 한다.
  - 1번을 해결했다고 가정하고 특정 시간이나 혹은 횟수 내에 락을 획득하지 못하면 Exception 발생한다. Exception이 발생한다면 finally 구문의 unlock()이 실행되어 락을 해제할 타이밍이 아닌데도 락을 해제시키기 때문에 작업이 수행중이더라도 다른 곳에서도 연산을 수행할 수 있게되어 동기화 보장이 되지 않는다. 

```
int maxRetry = 3;
int retry = 0;

while (!tryLock(lockKey)) {
    if (++retry == maxRetry) {
    throw new LockAcquisitionFailureException();
  }
  
  try {
    Thread.sleep(50;)
  } catch (InterruptedException e) {
    throw new RuntimeException(e);
  }
}

try { 
  // do process
} finally {
  unlock(lockKey);
}
```
이 문제는 단순히 try-finally 구문 밖에서 락 획득을 시도함으로써 해결할 수 있다.

- Redis에 많은 부하를 가하게 된다.
  - 위 코드는 스핀 락을 사용했지만 스핀 락을 사용하면 레디스에 엄청난 부담을 주게 된다. 스핀 락은 지속적으로 Lock의 획득을 시도하는 작업이기 때문에 Redis에 계속 요청을 보내게 되고 Redis는 이런 트래픽을 처리하느라 부담을 받게 된다.
- 결론 
  - 오픈소스 Redis Client인 Redisson 사용

## Redisson
### 분산 락을 어떻게 구현했을까?

Redisson은 Jedis, Lettuce 같은 자바 레드시 클라이언트이다.

Lettuce와 비슷하게 netty를 사용하여 non-blocking I/O를 사용한다. Redisson의 특이한 점은 직접 레디스의 명령어를 제공하지 않고, Bucket 이나 Map 같은 자료구조나 Lock 같은 특정한 구현체의 형태로 제공한다.

### 1. Lock에 타임아웃이 구현

Redisson은 tryLock 메소드에 타임아웃을 명시하도록 되어있다. 첫 번째 파라미터는 락 획득을 대기할 타임아웃이고, 두 번째 파라미터는 락이 만료되는 시간이다.

첫 번째 파라미터만큼의 시간이 지나면 false 가 반환다며 락 획득에 실패했다고 알려준다. 

두번째 파라미터만큼의 시간이 지나면 락이 만료되어 사라지기 때문에 어플리케이션에서 락을 해제해주지 않더라도 다른 스레드 혹은 어플리케이션에서 락을 획득할 수 있다.

이로 인해 무한 루프에 빠질 위험이 사라졌다.

```
public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException
```

### 2. 스핀 락을 사용하지 않는다.

Redisson은 기본적으로 스핀 락을 사용하지 않기 때문에 레디스에 부담을 주지 않는다.

Redisson은 pubsub 기능을 사용하여 스핀 락이 레디스에 주는 트래픽 부담을 줄였다. 락이 해제될 때마다 subscribe하는 클라이언트들에게 “너희는 이제 락 획득을 시도해도 된다” 라는 알림을 주어 일일이 레디스에 요청을 보내 락의 획득가능여부를 체크하지 않아도 되도록 개선됐다. 

Redisson의 Lock 획득 프로세스

1. 대기없는 tryLock 오퍼레이션을 하여 락 획득에 성공하면 true 값을 반환. 이는 경합이 없을 때 아무런 오버헤드 없이 락을 획득할 수 있도록 한다.
2. pubsub을 이용하여 메세지가 올 때까지 대기하다가 락이 해제되었다는 메세지가 오면 대기를 풀고 다시 락 획득을 시도한다. 락 획득에 실패하면 다시 락 해제 메세지를 기다린다. 이 프로세스를 타임아웃시까지 반복한다.
3. 타임아웃이 지나면 최종적으로 false를 반환하고 락 획득에 실패했음을 알린다. 대기가 풀릴 때 타임아웃 여부를 체크하므로 타임아웃이 발생하는 순간은 파라미터로 넘긴 타임아웃시간과 약간의 차이가 있을 수 있다.

### Lua 스크립트를 사용한다.

위와 같이 락의 기능을 제공하더라도 락에 사용되는 여러 연산은 atomic해야 한다. 그 이유는 각 명령어를 따로 보내게 되면 두 연산이 atomic하지 않게 수행되기 때문에 명령어의 실행 순서가 섞일 수 있어 예상과 다른 결과가 나올 수 있다.

- 락의 획득가능 여부 확인과 획득은 atomic해야 한다. 그렇지 않으면 획득이 가능하다고 응답받은 다음, 락 획득시도를 했는데 그 사이에 이미 다른 스레드에서 락을 획득해버려서 락 획득을 실패하는 경우가 생길 수 있다.

- 락의 해제와 pubsub 알림은 atomic해야 한다. 그렇지 않으면 락이 해제되고 바로 다른 스레드에서 락을 획득했을 때에도 락 획득을 시도해도 된다는 알림이 갈 수 있다.

레디스는 싱글 스레드 기반으로 연산하기 때문에 이러한 atomic 연산을 비교적 쉽게 구현할 수 있다. 그래서 레디스는 트랜잭션, Lua 스크립트로 atomic 연산을 지원한다.

트랜잭션은 명령어를 트랜잭션으로 묶는 기능이기에 명령어의 결과를 받아서 다른 연산에 활용하는 atomic한 연산을 구현하지 어렵다. 하지만 Lua 스크립트를 사용하면 atomic을 보장하는 스크립트를 쉽게 구현할 수 있다.

Redisson은 이러한 Lua 스크립트를 많이 활용하고 있습니다. RedissonLock에서도 Lua 스크립트를 사용하여 연산의 atomic을 보장하면서도, 레디스에 보내는 요청 수를 현저하게 줄여 성능을 높힌다.

```
// in RedissonLock.java
<T> RFuture<T> tryLockInnerAsync(long leaseTime, TimeUnit unit, long threadId, RedisStrictComman<T> command) {
  InternalLockLeaseTime = unit.toMillis(leaseTime);
  
  return commanExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE, command,
              "if (redis.call('exists', KEYS[1]) == 0) then " +
                  "redis.call('hset', KEYS[1], ARGV[2], 1); " +
                  "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                  "return nil; " +
              "end; " +
              "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
                  "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                  "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                  "return nil; " +
              "end; " +
              "return redis.call('pttl', KEYS[1]);",
                Collections.<Object>singletonList(getName()), internalLockLeaseTime, getLockName(threadId));
}
```
