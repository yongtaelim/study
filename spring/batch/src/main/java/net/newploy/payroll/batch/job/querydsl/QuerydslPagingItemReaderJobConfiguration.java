package net.newploy.payroll.batch.job.querydsl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.newploy.payroll.batch.entity.People;
import net.newploy.payroll.batch.querydsl.QuerydslPagingItemReader;
import net.newploy.payroll.batch.support.PeopleRepositorySupport;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class QuerydslPagingItemReaderJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private final PeopleRepositorySupport peopleRepositorySupport;

    private final int chunkSize = 10;

    @Bean
    public Job querydslPagingJob() {
        return jobBuilderFactory.get("QUERYDSL_JOB")
                .start(querydslPagingStep())
                .build();
    }

    @Bean
    public Step querydslPagingStep() {
        return stepBuilderFactory.get("QUERYDSL_STEP")
                .<People, People>chunk(chunkSize)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public QuerydslPagingItemReader<People> reader() {
        return new QuerydslPagingItemReader<>(entityManagerFactory, chunkSize, queryFactory -> peopleRepositorySupport.findAll());
    }

    private ItemWriter<People> writer() {
        return list -> {
            for (People people: list) {
                log.info("person={}", people);
            }
        };
    }
}
