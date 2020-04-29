# Circuit Breaker
reference
- https://bcho.tistory.com/1247

## MSA에서 서비스간 장애 전파
마이크로 서비스 아키텍쳐 패턴은 시스테믕ㄹ 여러개의 서비스 컴포넌트로 나눠서 서비스 컴포넌트간에 호출하는 개념을 가지고 있다. 이 아키텍쳐는 장점도 많지만 반대로 몇가지 단점을 가지고 있는데 그 중에 하나는 하나ㅡ이 컴포넌트가 느려지거나 장애가 나면 그 장애가 난 컴포넌트를 호출하는 종속된 컴포넌트까지 장애가 전파되는 특성을 가지고 있다.

Service A가 Service B를 호출하는 상황에서 Service B에 장애가 발생하여 Service A는 응답을 기다리는 상태로 잡혀있게 된다. 결과적으로 남은 쓰레드가 없어서 다른 요청을 처리할 수 없게 된다.

## Circuit breaker 패턴
이런 문제를 해결하는 디자인 패턴이 Circuit breaker 라는 패턴이 있다.
기본적인 원리로는 서비스 호출 중간 측 위의 예제에서는 Service A와 Service  B에 Circuit Breaker를 설치한다. Service B로의 모든 호출은 이 Circuit Breaker를 통하게 되고 Service B가 정상적인 상황에서는 트래픽을 문제없이 bypass 한다.

만약에 Service B가 문제가 생겼음을 Circuit breaker가 감지한 경우에는 Service B로의 호출을 강제적으로 끊어서 Service A에서 쓰레드들이 더 이상 요청을 기다리지 않도록 해서 장애가 전파하는 것을 방지한다. 강제적으로 호출을 끊으면 에러 메시지가 Service A에서 발생하기 때문에 장애 전파는 막을 수 있지만, Service A에서 이에 대한 **장애 처리 로직**이 별도로 필요하다.

이를 조금 더 발전 시킨것이 Fall-back 메시징인데, Circuit breaker에서 Service B가 정상적인 응답을 할 수 없을 때, Circuit breaker가 룰에 따라서 다른 메세지를 리턴하게 하는 방법이다.  
이 패턴은 넷플릭스에서 자바 라이브러리인 `Hystrix`로 구현이 되었으며, Spring 프레임웍을 통해서도 손쉽게 적용할 수 있다.  
이렇게 소프트웨어 프레임웍 차원에서 적용할 수 있는 방법도 있지만 인프라 차원에서 Circuit breaker를 적용하는 방법도 있는데, `envoy.io`라는 프록시 서버를 이용하면 된다.

## Hystricx
Hystrix는 Circuit breaker 패턴을 자바 기반으로 호픈소스화한 라이브러리이다.  
Circuit breaker 자체를 구현한 것 뿐만 아니라, 각 서비스의 상태를 한눈에 알아볼 수 있도록 대쉬보드를 같이 제공한다.

### 사용방법
TODO.... 