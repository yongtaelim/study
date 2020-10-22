package net.newploy.payroll.batch.job.mybatis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.newploy.payroll.batch.entity.People;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MyBatisPagingItemReaderJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory sqlSessionFactory;

    private int chunkSize = 10;

    @Bean
    public Job MyBatisPagingItemReaderJob() {
        return jobBuilderFactory.get("MyBatisPagingItemReaderJob")
                .start(MyBatisPagingItemReaderStep())
                .build();
    }

    @Bean
    public Step MyBatisPagingItemReaderStep() {
        return stepBuilderFactory.get("MyBatisPagingItemReaderStep")
                .<People, People>chunk(chunkSize)
                .reader(MyBatisPagingItemReader())
                .writer(MyBatisPagingItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public MyBatisPagingItemReader<People> MyBatisPagingItemReader() {
        Map<String, Object> datesParameters = new HashMap<>();
        datesParameters.put("enabled", 1);

        return new MyBatisPagingItemReaderBuilder<People>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("net.newploy.payroll.batch.mapper.PeopleMapper.selectById")
                .parameterValues(datesParameters)
                .pageSize(chunkSize)
                .build();
    }

//    @StepScope
//    @Bean
//    public Map<String, Object> datesParameters(
//            @Value("#{jobExecutionContext['store_id']}") Long storeId) {
//        log.info(">>>>>>>>>> storeId={}",storeId);
//        Map<String, Object> map = new HashMap<>(1);
//        map.put("storeId", storeId);
//        return map;
//    }

    private ItemWriter<People> MyBatisPagingItemWriter() {
        return list -> {
            for (People person: list) {
                log.info("Current person={}", person);
            }
        };
    }
}
