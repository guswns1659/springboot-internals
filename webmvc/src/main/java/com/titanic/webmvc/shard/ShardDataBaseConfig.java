package com.titanic.webmvc.shard;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@RequiredArgsConstructor
@EnableJpaRepositories(
        entityManagerFactoryRef = "shardEntityFactory",
        transactionManagerRef = "shardTransactionManager",
        basePackages = "com.titanic.webmvc.shard.repository"
)
@EnableTransactionManagement
public class ShardDataBaseConfig {

    private final Environment env;
    private final ShardDataSourceProperties shardDataSourceProperties;

    // EntityManagerFactoryBean
    @Bean("shardEntityFactory")
    public LocalContainerEntityManagerFactoryBean shardEntityFactory(
            @Value("${spring.shard-datasource.type}") DataSourceType dataSourceType
    ) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        switch (dataSourceType) {
            case main:
                factory.setDataSource(mainDataSource());
                break;
            case child:
                factory.setDataSource(null);
            default:
                throw new IllegalStateException("Main, child datasource doesn't exist");
        }

        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPackagesToScan("com.titanic.webmvc.shard.entity");

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.shard-datasource.ddl-auto"));
        factory.setJpaPropertyMap(properties);

        return factory;
    }

    private DataSource mainDataSource() {
        // DataSource Router set several datasources
        ShardDataSourceRouter dataSourceRouter = new ShardDataSourceRouter();

        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        shardDataSourceProperties.getMain().getShards().forEach(shard -> {
            dataSourceMap.put(shard.getName(), createDataSource(shard, shardDataSourceProperties));
        });

        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.afterPropertiesSet();

        return dataSourceRouter;
    }

    private DataSource createDataSource(ShardDataSourceProperties.Shard shard, ShardDataSourceProperties properties) {

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setUsername(shard.getName());
        hikariConfig.setPassword(properties.getPassword());
        hikariConfig.setJdbcUrl(shard.getUrl());
        hikariConfig.setDriverClassName(properties.getDriverClassName());
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setConnectionTimeout(5000);

        return new HikariDataSource(hikariConfig);
    }


    @Bean("shardTransactionManager")
    public PlatformTransactionManager shardTransactionManager(
            @Qualifier("shardEntityFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
