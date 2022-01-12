package com.titanic.webmvc.shard;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("sharding")
public class ShardingConfig {

    private ShardingStrategy strategy;

    @Value("${sharding.rule.mod}")
    private int mod;

    public enum ShardingStrategy {
        MODULAR,
    }
}
