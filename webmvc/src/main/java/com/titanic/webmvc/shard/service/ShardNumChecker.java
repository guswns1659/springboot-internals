package com.titanic.webmvc.shard.service;

import com.titanic.webmvc.shard.ShardDb;
import com.titanic.webmvc.shard.ShardingConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
