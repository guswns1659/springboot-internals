package com.titanic.webmvc.shard;

public class ShardDb {

    private static final String SHARD_DB_PREFIX = "SHARD";
    private static final String SHARD_DB_DELIMITER = "-";
    private static final String DEFAULT_SHARD_DB_NUM = "00";

    private final String shardNum;

    public ShardDb(Integer shardNum) {
        if (shardNum == null || shardNum < 0) {
            throw new IllegalStateException("ShardNum has to be not null and positive");
        }

        this.shardNum = String.format("%02d", shardNum);
    }

    public static String getDefaultLookupKey() {
        return SHARD_DB_PREFIX + SHARD_DB_DELIMITER + DEFAULT_SHARD_DB_NUM;
    }

    public String getDbLookupKey() {
        return SHARD_DB_PREFIX + SHARD_DB_DELIMITER + shardNum;
    }
}
