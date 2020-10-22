package net.newploy.payroll.batch.job.mybatis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.newploy.payroll.batch.entity.People;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MyBatisBatchItemWriterJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory sqlSessionFactory;

    private static final int chunkSize = 10;

    @Bean
    public Job mybatisBatchItemWriterJob() {
        return jobBuilderFactory.get("jdbcBatchItemWriterJob")
                .start(mybatisBatchItemWriterStep())
                .build();
    }

    @Bean
    public Step mybatisBatchItemWriterStep() {
        return stepBuilderFactory.get("jdbcBatchItemWriterStep")
                .<People, People>chunk(chunkSize)
                .reader(mybatisBatchItemWriterReader())
                .processor(mybatisCompositeProcessor())
                .writer(mybatisBatchItemWriter())
                .build();
    }

    @Bean
    public MyBatisPagingItemReader<People> mybatisBatchItemWriterReader() {
        Map<String, Object> datesParameters = new HashMap<>();
        datesParameters.put("enabled", 1);

        return new MyBatisPagingItemReaderBuilder<People>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("net.newploy.payroll.batch.mapper.PeopleMapper.selectById")
                .parameterValues(datesParameters)
                .pageSize(chunkSize)
                .build();
    }

    @Bean
    public CompositeItemProcessor mybatisCompositeProcessor() {
        List<ItemProcessor> delegates = new ArrayList<>(2);

        delegates.add(processor1());
        delegates.add(processor2());

        CompositeItemProcessor processor = new CompositeItemProcessor<>();

        processor.setDelegates(delegates);

        return processor;
    }

    public ItemProcessor<People, People> processor1() {
        return person -> {
            log.info("안녕하세요. "+ person.getFirstName() + "입니다.");
            return person;
        };
    }

    public ItemProcessor<People, People> processor2() {
        return person -> {
            log.info("안녕하세요. "+ person.getLastName() + "입니다.");
            return person;
        };
    }

    @Bean
    public CompositeItemWriter<People> mybatisBatchItemWriter() {
        CompositeItemWriter compositeItemWriter = new CompositeItemWriter();
        List<ItemWriter<?>> writers = new ArrayList<>(4);
        writers.add(mybatisBatchItemWriterUpdate());
        writers.add(mybatisBatchItemWriterInsert());
        compositeItemWriter.setDelegates(writers);
        return compositeItemWriter;
    }

    @Bean
    public MyBatisBatchItemWriter<People> mybatisBatchItemWriterUpdate() {
        return new MyBatisBatchItemWriterBuilder<People>()
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("net.newploy.payroll.batch.mapper.PeopleMapper.updateByEnabled")
                .itemToParameterConverter(createItemToParameterMapConverter(0))
                .build();
    }

    @Bean
    public MyBatisBatchItemWriter<People> mybatisBatchItemWriterInsert() {
        return new MyBatisBatchItemWriterBuilder<People>()
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("net.newploy.payroll.batch.mapper.PeopleMapper.insert")
                .itemToParameterConverter(createItemToParameterMapConverter(1))
                .build();
    }

    private <T> Converter<T, Map<String, Object>> createItemToParameterMapConverter(Integer enabled) {
        return item -> {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("item", item);
            parameter.put("enabled", enabled);
            return parameter;
        };
    }

}
