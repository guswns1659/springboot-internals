package com.titanic.batch.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ConditionalJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MoneyHasDataTasklet moneyHasDataTasklet;
    private final SimpleTasklet simpleTasklet;

    @Bean
    public Job conditionalJob() {
        return jobBuilderFactory
            .get("conditionalJob")
            .incrementer(new RunIdIncrementer())
            .start(moneyHasDataStep())
            .on("TRUE").to(moneyStep())
            .from(moneyHasDataStep()).on("FALSE").to(archiveStep())
            .end()
            .build();
    }

    @Bean
    public Step moneyHasDataStep() {
        return stepBuilderFactory
            .get("moneyHasDataStep")
            .tasklet(moneyHasDataTasklet)
            .listener(new JackStepListener())
            .build();
    }

    @Bean
    public Step moneyStep() {
        return stepBuilderFactory
            .get("moneyStep")
            .listener(new JackStepListener())
            .tasklet(simpleTasklet) // 그냥 실행
            .build();
    }

    @Bean
    public Step archiveStep() {
        return stepBuilderFactory
            .get("archiveStep")
            .listener(new JackStepListener())
            .tasklet(simpleTasklet)
            .build();
    }
}
