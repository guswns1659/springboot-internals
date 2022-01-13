package com.titanic.webmvc.shard;

import lombok.Data;

@Data
public class UserHolder {

    public static ThreadLocal<Context> context = new ThreadLocal<Context>();

    public static void setContext(int shardNum) {
        ShardDB shardDB = new ShardDB(String.valueOf(shardNum));
        Context contextWithShardDb = new Context(shardDB);
        context.set(contextWithShardDb);
    }

    @Data
    public static class Context {
        private ShardDB shardDB;

        public Context(ShardDB shardDB) {
            this.shardDB = shardDB;
        }
    }
}
