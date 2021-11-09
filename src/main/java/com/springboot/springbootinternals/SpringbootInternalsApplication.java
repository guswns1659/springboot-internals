package com.springboot.springbootinternals;

import com.springboot.springbootinternals.kafka.JackProducer;
import com.springboot.springbootinternals.kafka.JackProducerConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.Filter;

@ComponentScan(
        excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {JackProducer.class, JackProducerConfig.class}))
@SpringBootApplication
@EnableBatchProcessing
public class SpringbootInternalsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootInternalsApplication.class, args);
    }

}
