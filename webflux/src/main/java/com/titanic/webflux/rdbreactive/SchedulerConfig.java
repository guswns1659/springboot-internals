package com.titanic.webflux.rdbreactive;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {

    private static final int DEFAULT_THREAD_HIKARI_POOL = 10;
    private final ApplicationContext applicationContext;

    @Bean("jdbcScheduler")
    public Scheduler jdbcScheduler() {

        HikariDataSource dataSource = Optional
                .ofNullable(applicationContext.getBeanProvider(HikariDataSource.class).getIfAvailable())
                .orElseGet(() -> {
                    HikariConfig config = new HikariConfig();
                    config.setMaximumPoolSize(DEFAULT_THREAD_HIKARI_POOL);
                    return new HikariDataSource(config);
                });

        return Schedulers.fromExecutor(
                Executors.newFixedThreadPool(dataSource.getMaximumPoolSize(),
                        new CustomizableThreadFactory("jdbc-"))
        );
    }
}
