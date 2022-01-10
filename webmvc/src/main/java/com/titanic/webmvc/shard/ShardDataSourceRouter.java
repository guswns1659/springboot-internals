package com.titanic.webmvc.shard;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

public class ShardDataSourceRouter extends AbstractRoutingDataSource {

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return UserContextHolder.getShardDbName().orElse(ShardDb.getDefaultLookupKey());
    }
}
