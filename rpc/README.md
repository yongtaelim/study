# RPC
reference : <https://brunch.co.kr/@springboot/202>
## Feign
### 개요
- Netflix에서 개발한 Http Client Binder 이다.
- 선언적 웹서비스 클라이언트
- Spring Cloud 프로젝트에서 매핑하여 제공되고 있다.
- 쉽게 Web Service Client를 만들 수 있다.
- Feign 사용방법으로 `interface`를 만들고 `annotaion`을 붙힌다.
### @EnableFeignClients
```java
package com.example.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RpcApplication {
	public static void main(String[] args) {
		SpringApplication.run(RpcApplication.class, args);
	}
}
```
최상위 Package에 선언한다. `@EnableFeignClients` 어노테이션을 붙히면, `@FeignClient`를 선언한 `interface`를 찾아서 구현체를 자동으로 만들어준다.

### feign interface
```java

package com.example.rpc.client;

import com.example.rpc.config.FeignHeaderConfiguration;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "feign-api",
            url = "http://localhost:8081/",
            configuration = {FeignHeaderConfiguration.class})
public interface FeignRpcInterface {

    /**
     * url : http://localhost:8080/items
     * type : GET
     * @return
     */
    @GetMapping(value = "/items")
    String getItems();

    /**
     * url : http://localhost:8080/items/{id}
     * type : GET
     * @param id
     * @return
     */
    @GetMapping(value = "/items/{id}")
    String getItem(@PathVariable("id") Long id);

    /**
     * url : http://localhost:8080/items
     * type : GET
     * header : {"key1" : "value1"}
     * @return
     */
    @GetMapping(value = "/items/headers", headers = "key1=value1")
    String getItemsHeaders1();

    /**
     * url : http://localhost:8080/items
     * type : GET
     * header : {"key1" : "value1"}
     * @param headers
     * @return
     */
    @GetMapping(value = "/items/headers")
    String getItemsHeaders2(@RequestHeader("key1") String headers);
}
```


## RestTemplate
- 간편하게 Rest 방식 api를 호출할 수 있는 spring 내장 클래스이다.
- RESTful 형식에 맞춘다.
- Spring 3.0부터 지원 가능
- json, xml 응답을 모두 받을 수 있다.

## Feign vs RestTemplate

- RestTemplate
  - 시간이 지날수록 유지보수하기 어렵다.
  - 불필요한 코드를 반복적으로 작성해야한다.
  - 테스트하기도 쉽지않다.
- Feign
  - 선언적으로 interface를 작성하고 annotation을 선언만 하면 되기 때문에 심플하다.
  - 테스트도 간편하다. 

참고 예제 소스 : <https://github.com/yongtaelim/study/tree/master/rpc>

## Fixture
- 선택1 : <https://www.mockable.io/> 를 이용하여 Mockup API Server를 띄워 테스트한다.
  - 방법 : <https://ricepower.tistory.com/10> 참조
- 선택2 : demo-server project 생성 후 띄운다.  

## TODO
Retrofit 학습....