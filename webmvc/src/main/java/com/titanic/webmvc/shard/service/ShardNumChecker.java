package com.titanic.webmvc.shard.service;

import com.titanic.webmvc.shard.ShardDb;
import com.titanic.webmvc.shard.ShardingConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.config.activate.on-profile", havingValue = "shard")
public class ShardNumChecker {

    private final ShardingConfig shardingConfig;

    public ShardDb processSharding(Long userId) {
        switch (shardingConfig.getStrategy()) {
            case MODULAR:
            default:
                return new ShardDb((int) (userId % shardingConfig.getMod()));
        }
    }
}
