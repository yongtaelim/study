# Collections
reference 
- https://perfectacle.github.io/2017/08/02/vector-array-list-linked-list/
- https://d2.naver.com/helloworld/831311
## List
### ArrayList
- Array 라는 이름으로만 보아도 `index`를 가지고 있어서 검색에 용이하다.
- `insert`, `delete`에는 적합하지 않다. 이유로는 중간에 추가/삭제가 이루어져 데이터가 shift되기 때문이다.
- Vertor와 달리 동기화를 보장하지 않는다.
- 공간이 모자를 때는 모자른 만큼만 공간을 확보한다.

### LinkedList
- Node(데이터와 다음 노드로 연결시킬 주소지)들이 줄줄이 연결된 구조이다.
- 맨 마지막의 데이터를 검색한다면 처음부터 끝까지 노드를 타고 이동하기 때문에 **검색에 적합하지 않다.**
- `insert`, `delete` 에 적합하다. 이유로는 중간의 해당 노드의 주소미나 바꿔주면 되므로 빠르다.

## Map
### HashMap
key에 대한 해시 값을 사용하여 값을 저장하고 조회하며, 키-값 쌍의 개수에 따라 동적으로 크기가 증가하는 `associate array`(배열 연결)라고 할수 있다.
 
#### 해시 분포와 해시 충돌
`X.equals(Y)` 가 false 일때 X.haseCode() != Y.hashCode()가 같이 않다면, 이때 사용하는 해시 함수는 완전한 해시 함수라고 한다.
Boolean같이 서로 구별된느 개체의 종류가 적거나, Integer, Long, Double같은 Number 객체는 객체가 나타내려는 값 자체를 해시값을 사용할 수 있기 때문에 완전한 해시 함수 대상이다.
하지만 String이나 POJO에 대해서는 완전한 해시함수가 불가능하다.

HashMap은 기본적으로 각 각체의 hashCode() 메서드가 반환하는 값을 사용하는 데, 결과 자료형은 int이다. 32비트 정수 자료형으로는 완전한 자료 해시 함수를 만들 수 없다.

따라서 HashMap을 비롯한 많은 해시 함수를 이용하는 associative array 구현체에서는 메모리를 절약하기 위해 실제 해시 함수의 표현 정수 범위`N`보다 작은 `M`개의 원소가 있는 배열만 사용한다. 따라서 다음과 같이 객체에 대한 해시 코드의 나머지 값을 해시 버킷 인덱스 값으로 사용한다.

```
int index = X.hashCode() & M;
``` 

이 코드와 같은 방식을 사용하면, 서로 다른 해시코드를 가지는 서로 다른 객체가 1/M의 확률로 같은 해시 버킷을 사용하게 된다. 이는 해시 함수가 얼마나 해시 충돌을 회피하도록 잘 구현되었느냐에 상관없이 발생할 수 있는 또 다른 종류의 해시 충돌이다. 이렇게 해시 충돌이 발생하더라도 키-값 쌍 데이터를 잘 저장하고 조회할 수 있게 하는 방식에는 대표적으로 두가지가 있다.
1. Open Addressing  
- 데이터를 삽입하려는 해시 버킷이 이미 사용 중인 경우 다른 해시 버킷에 해당 데이터를 삽입하는 방식이다.
2. Separate Chaining
- 각 배열의 인자는 인덱스가 같은 해시 버킷을 연결한 링크드 리스트의 첫 부분이다.

HashMap에서 사용하는 방식은 Separate Channing 방식이다. Open Addressing은 데이터를 삭제할 때 처리가 효율적이지 않은데, hashMap에서는 remove가 매우 빈번하게 호출될 수 있기 때문이다.