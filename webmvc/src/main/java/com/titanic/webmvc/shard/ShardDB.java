package com.titanic.webmvc.shard;

import lombok.Data;

@Data
public class ShardDB {
    private String name;

    public ShardDB(String name) {
        this.name = name;
    }
}
