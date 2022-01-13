package com.titanic.webmvc.shard;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.shard-datasource")
public class ShardDataSourceProperties {
    private String username;
    private String password;
    private String driverClassName;

    private Shards main;
    private Shards child;

    @Data
    public class Shards {
        private List<Shard> shards;
    }

    @Data
    public class Shard {
        private String name;
        private String url;
    }
}
