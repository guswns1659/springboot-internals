package com.titanic.webmvc.shard;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.titanic.webmvc.shard.ShardDataBaseProperty.Shard;

@Slf4j
@EnableJpaAuditing
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "shardEntityManagerFactory",
        transactionManagerRef = "shardTransactionManager",
        basePackages = "com.titanic.webmvc.shard.repository"
)
@RequiredArgsConstructor
@EnableTransactionManagement
@ConditionalOnProperty(name = "spring.config.activate.on-profile", havingValue = "shard")
public class ShardDataBaseConfig {

    private final Environment env;
    private final ShardDataBaseProperty shardDataBaseProperty;

    @Bean
    @ConditionalOnProperty(name = "spring.shard-datasource.type", havingValue = "main")
    public DataSource shardDataSourceMain() {
        Map<Object, Object> dataSourceMap = getDataSourceMap(false);

        ShardDataSourceRouter router = new ShardDataSourceRouter();
        router.setTargetDataSources(dataSourceMap);
        router.afterPropertiesSet();

        return new LazyConnectionDataSourceProxy(router);
    }

    private Map<Object, Object> getDataSourceMap(boolean isChild) {
        List<Shard> shards;
        if (isChild) {
            shards = shardDataBaseProperty.getChild().getShards();
        } else {
            shards = shardDataBaseProperty.getMain().getShards();
        }

        Map<Object, Object> dataSourceMap = new LinkedHashMap<>();
        shards.forEach(shard ->
                dataSourceMap.put(shard.getName(),
                        createDataSource(shard, shardDataBaseProperty, isChild)));

        return dataSourceMap;
    }

    private DataSource createDataSource(Shard shard, ShardDataBaseProperty shardDataBaseProperty, boolean isChild) {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(shard.getUrl());
        config.setUsername(shardDataBaseProperty.getUsername());
        config.setPassword(shardDataBaseProperty.getPassword());
        config.setDriverClassName(shardDataBaseProperty.getDriverClassName());
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(5000);
        config.setReadOnly(isChild);

        return new HikariDataSource(config);
    }

    // TODO(jack.comeback) : Need to impl
    @Bean
    @ConditionalOnProperty(name = "")
    public DataSource shardDataSourceChild() {
        return null;
    }

    @Bean(name = "shardEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean shardEntityManagerFactory(
            @Value("${spring.shard-datasource.type}") DatasourceType datasourceType
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        switch (datasourceType) {
            case main:
                em.setDataSource(shardDataSourceMain());
                break;
            case child:
                em.setDataSource(shardDataSourceChild());
                break;
            default:
                throw new BeanCreationException("spring.shard-datasource.type 설정 필요");
        }

        em.setPackagesToScan("com.titanic.webmvc.shard.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>();
        // TODO(jack.comeback) : properties Map 설정 추가 필요
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.shard-datasource.ddl-auto"));

        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "shardTransactionManager")
    public PlatformTransactionManager shardTransactionManager(
            @Qualifier("shardEntityManagerFactory")EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
