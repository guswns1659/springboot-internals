package com.titanic.webmvc.shard;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class UserContextHolder {

    private static final ThreadLocal<Context> userContext = new ThreadLocal<Context>();

    public static ThreadLocal<Context> getUserContext() {
        return userContext;
    }

    public static void setShardDb(ShardDb shardDb) {
        Context context = new Context();
        context.setShardDb(shardDb);
        getUserContext().set(context);
    }

    public static Optional<String> getShardDbName() {
        return Optional.ofNullable(getUserContext())
                .map(ThreadLocal::get)
                .map(Context::getShardDb)
                .map(ShardDb::getDbLookupKey);
    }

    public static void clearShardDb() {
        getUserContext().get().setShardDb(null);
    }

    @Data
    protected static class Context {

        private ShardDb shardDb;
    }
}
