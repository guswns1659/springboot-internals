package com.titanic.webmvc.shard;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "spring.shard-datasource")
public class ShardDataBaseProperty {

    private String username;
    private String password;
    private String driverClassName;

    private ShardDataBase main;
    private ShardDataBase child;

    @Data
    public static class ShardDataBase {

        private List<Shard> shards;
    }

    @Data
    public static class Shard {

        private String name;
        private String url;
    }
}
