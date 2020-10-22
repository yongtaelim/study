# spring batch

## GET STARTED
`https://github.com/jojoldu/spring-batch-in-action` 참조해서 작성

schema-mysql.sql 실행 후 하위 쿼리 실행
```sql
create table people
(
    person_id  bigint auto_increment      not null
        primary key,
    first_name varchar(20) null,
    last_name  varchar(20) null,
    enabled    int         null
);
```
## simple
Job, Step 사용법
```
DeciderJobConfiguration.java
SimpleJobConfiguration.java
StepNextConditionalJobConfiguration.java
StepNextJobConfiguration.java
```

## JDBC
```
JdbcPagingItemReaderJobConfiguration.java
```

## JPA
```
JpaPagingItemReaderJobConfiguration.java
```

## MyBatis
```
MyBatisBatchItemWriterJobConfiguration.java
MyBatisPagingItemReaderJobConfiguration.java
```