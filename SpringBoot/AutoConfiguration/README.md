# AutoConfiguration
참조  
http://dveamer.github.io/backend/SpringBootAutoConfiguration.html

## 개요

Spring Boot는 Spring과 마찬가지로 component-scan을 통해 component들을 찾고 bean 생성을 진행한다. 그 과정에서 설정한 bean들이 생성된다. 

- @Controller
- @RestController
- @Service
- @Repository
- @Configuration에 의해 추가적인 bean

Spring에서는 ThreadPoolTaskExecutor를 사용하기 위해서는 우리가 해당 bean을 등록해야하지만 Spring Boot에서는 등록하지 않아도 해당 bean이 자동으로 생성되기 때문에 사용할 수 있다.

## @EnableAutoConfiguration

auto configuration 기능을 사용하겠다는 설정이다. 일반적으로 아래와 같이 @ComponentScan과 함께 사용

```java
package com.dveamer.sample;

@SpringBootConfiguration
@ComponentScan("com.dveamer.sample")
@EnableAutoConfiguration
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

@ComponentScan에 입력된 값(com.dveamer.sample)은 component scan을 시작할 패키지 위치이다.

com.dveamer.sample 하위 모든 패키지를 component scan 범위로 잡겠다는 설정이다. package 위치를 입력하지 않는다면 com.dveamer.sample.Application이 놓여진 패키지(com.dveamer.sample)가 기본 값으로 사용된다. 여러 패키지 위치를 scan 대상으로 설정하는 것도 가능하다.

글의 시작점에서 설명했듯이 component scan을 통해서 모은 component들의 정보와 Spring Boot가 spring.factories 파일에 사전에 정의한 AutoConfiguration 내용에 의해 bean 생성이 진행된다.

만약에…

- 우리가 정의한 bean과 Spring Boot가 정읳나 bean이 충돌이 발생하면 어떻게 될까?
- AutoConfiguration의 종류가 굉장히 많은데 우리에게 필요한 설정은 몇개 되지 않는다면 쓸데없는 bean이 생성되지 않을까?
- 위와 같은 과정에서 등록되지 않은 라이브러리를 참조하면 문제가 발생하지 않을까?

Spring Boot는 @Condition과 @Conditional을 이용해서 이와 같은 문제를 해결하여 AutoConfiguration 기능을 우리에게 제공한다.

## Auto Configuration Filters & Conditions

Spring boot가 미리 정의해둔 AutoConfiguration 정보는 spring-boot-autoconfigure/META-INF/spring.factories에서 혹은 spring.factories에서 확인 가능하다.

org.springframework.boot.autoconfigure.EnableAutoConfiguration에 상당히 많은 AutoConfiguration이 등록되어 있는 것을 확인할 수 있다.

```properties
...(생략)

# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,\
...(생략)
```

각 AutoConfiguration들은 필요한 상황에만 자신이 실행될 수 있도록 @Conditional, @Condition과 같은 annotation들로 설정되어있다. 그 annotaion을 기반으로 필터링이 먼저 이뤄지고 필터링되지 ㅏㅇㄶ은 AutoConfiguration을 가지고 작업이 진행된다.

@Condition, @Conditional은 Spring4.0부터 추가된 annotiona이도 Spring Boot auto configuration 과정에서 사용되는 또 다른 annotaion들도 autoconfigure-condition에서 확인 가능하다.

@Profile, @Lazy와 같은 Spring에ㅓㅅ 제공하는 다른 annotation들도 Spring Boot auto configuration에 활용된다.

Auto Configuration Import Filters와 몇가지 @Conditional을 살펴보는 과정을 통해 AutoConfiguration의 원리를 살펴본다.

### Auto Configuration Import Filters

Spring Boot는 spring.factories 정보를 가지고 auto configuration을 진행한다.

그 내용 중에 AutoConfigurationImportFilter 관련 설정이 있으며 아래와 같은 3개의 필터가 적용 된 것을 확인할 수 있다.

```properties
# Auto Configuration Import Filters
org.springframework.boot.autoconfigure.AutoConfigurationImportFilter=\
org.springframework.boot.autoconfigure.condition.OnBeanCondition,\
org.springframework.boot.autoconfigure.condition.OnClassCondition,\
org.springframework.boot.autoconfigure.condition.OnWebApplicationCondition
```

해당 필터들은 각 AutoConfiguration이 가진 @Conditional을 가지고 조건 만족여부를 체크한다. 그리고 조건이 맞지 않을 경우 해당 AutoConfiguration이 동작

`org.springframework.boot.autoconfigure.condition.OnBeanCondition`
- 특정 bean들의 존재유무에 대해서 다루는 필터
- 대상 : @ConditionalOnBean, @ConditionalOnMissingBean, @ConditionalOnSingleCandidate

`org.springframework.boot.autoconfigure.condition.OnClassCondition`
- 특정 class들의 존재유무에 대해서 다루는 필터
- 대상 : @ConditionalOnClass, @ConditionalOnMissingClass

`org.springframework.boot.autoconfigure.condition.OnWebApplicationCondition`
- WebApplicationContext의 존재유무에 대해서 다루는 필터
- 대상 : @ConditionalOnWebApplication, @ConditionalOnNotWebApplication

### @CondisionalOnMissingBean

특정 bean이 사전에 생성되지 않은 경우 조건이 만족된다. @Bean과 함께 사용된다면 이미 생성된 bean이 없을 때 해당 bean을 생성한다는 의미로 보시면 된다.

우리가 특정 bean을 생성하도록 설정해놨다면, 일반적으로 AutoConfiguration의 bean 생성 순서가 마지막에 오도록 AutoConfiguration이 잘 짜여져있기 때문에 우리가 설정한 bean이 먼저 생성되고 해당 AutoConfiguration은 필터링 되어 중복생성되는 상황을 막는다. 우리가 해당 bean을 설정하지 않았다면 AutoConfiguration에서는 해당 bean을 자동 생성하게 된다.

ThreadPoolTaskExecutor bean을 생성하는 예시

```java
@Configuration(proxyBeanMethods = false)
public class TaskExecutionAutoConfiguration {

    /**
     * Bean name of the application
     */
    public static final String APPLICATION_TASK_EXECUTOR_BEAN_NAME = "applicationTaskExecutor";

    @Lazy
    @Bean(name = { APPLICATION_TASK_EXECUTOR_BEAN_NAME,
        AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME })
    @ConditionalOnMissingBean(Executor.class)
    public ThreadPoolTaskExecutor applicationTaskExecutor(TaskExecutorBuilder builder) {
        System.out.println("create a bean " + APPLICATION_TASK_EXECUTOR_BEAN_NAME);
        return builder.build();
    }

}
```

@Lazy가 걸려있기 때문에 Spring Boot 기동시에 생성되지 않고 ThreadPoolTaskExecutor가 필요한 상황에서 bean이 생성 요청된다. Executor.class와 같은 class type인 bean이 이미 생성되지 않은 경우에 @ConditionalOnMissingBean 조건이 만족되고 bean 생성이 진행된다. 아래와 같은 Executor bean을 생성하는 설정을 해뒀다면 우리가 설정한 bean이 생성되고 TaskExecutionaAutoConfiguration에 의해서는 bean 생성이 이루어지지 않는다. 반대로 우리가 Executor bean 등록을 설정하지 않았더라도 필요한 상황이되면 해당 bean이 생성되게 된다.

`CustomizedAsyncConfig.java`

```java
@Configuration
public class CustomizedAsyncConfig {

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(30);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("Executor-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
```

### @ConditionalOnBean

특정 bean이 이미 생성되어있는 경우에만 조건이 만족된다. 작업을 위해 필수적으로 필요한 bean이 미리 생성되어있는지 체크할 때 사용할 수 있다.

예로 JdbcTemplate를 생성하기 위해서는 DataSource가 필요합니다. 아래의 JdbcTemplate bean 생성 설정은 @ConditionalOnBean이 함께 사용되어 dataSource라고 정의된 bean이 존재할 때만 JdbcTemplate bean을 생성합니다. 만약에 dataSource가 존재하지 않는다면 JdbcTemplate을 만들 수도 없을 뿐더러 만들 필요가 없기 때문에 auto configuration 과정에서 JdbcTemplate을 bean 생성을 진행하지 않는다.

`JdbcTemplateAutoConfiguration.java`

```java
public class jdbc {
    @Bean
    @ConditionalOnBean(name={"dataSource"})
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
      return new JdbcTemplate(dataSource);
    }
}
```

### @ConditionalOnClass

classpath에 특정 class가 존재할 때만 조건이 만족된다. 작업을 위해 필수적으로 필요한 의존성이 등록되어있는지 체크할 때 사용한다.

H2 DB 콘솔 화면을 자동으로 구성하는 내용으로 예로 들어본다.

`H2ConsoleAutoConfig.java`

```java
package org.springframework.boot.autoconfigure.h2;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(H2ConsoleProperties.class)
//@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@ConditionalOnProperty(prefix = "spring.h2.console", name = "enabled", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(H2ConsoleProperties.class)
public class H2ConsoleAutoConfiguration {

    private final String H2_CONSOLE_AUTO_CONFIGURATION_BEAN_NAME = "h2-configuration-bean-name";

    @Bean(name = H2_CONSOLE_AUTO_CONFIGURATION_BEAN_NAME)
    public void h2Console() {
        System.out.println("create a bean " + H2_CONSOLE_AUTO_CONFIGURATION_BEAN_NAME);
    }
}
```

WebServlet.java 파일이 classpath에 존재해야지만 @ConditionalOnClass의 조건이 만족된다. 결국 WebServlet.java를 가진 spring-boot-stater-web과 같은 의존성이 추가되어있는 상황에서만 H2ConsoleAutoConfiguration은 동작하게 된다. 

- @ConditionalOnWebApplication : servlet 타입의 web application 일 경우
- @ConditionalOnProperty : 프로퍼티에 spring.h2.console.enabled=true가 있는 경우
- @AutoConfigureAfter : DataSourceAutoConfiguration이 먼저 진행 된 후에 처리된다.
- @EnableConfigurationProperties : H2ConsoleProperties를 이용해서 관련 프로퍼티 정보를 읽어온다.

위 조건들 모두가 만족된다면 `h2Console` bean이 생성되고 System.out.println으로 “create a bean h2-configuration-bean-name 출력된다.

`build.gradle`

```properties
runtimeOnly 'com.h2database:h2
```

`application.properties`
```properties
spring.h2.console.enabled=true
```

### @SpringBootApplication

@SpringBootApplication는 @ComponentScan과 @EnableAutoConfiguration을 포함하고 있다.

아래의 Application.java는 @SpringBootApplication 설정만 했지만 component scan과 auto configuration이 이뤄진다.

`AutoconfigurationApplication.java`

```java
//@SpringBootConfiguration
//@ComponentScan(value = "com.example.autoconfiguration")
//@EnableAutoConfiguration
// or
@SpringBootApplication
public class AutoconfigurationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoconfigurationApplication.class, args);
	}

}
```

프로젝트 최상단에 Application.java를 위치시키고 @SpringBootApplication 설정해두면 앞으로 프로젝트에 추가할 configuration 관련 정보들이 모두 유효하다.