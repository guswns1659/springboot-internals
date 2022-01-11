package com.titanic.webmvc.shard.entity;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserTx {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    public UserTx() {
    }

    @Builder
    public UserTx(Long id, String type) {
        this.id = id;
        this.type = type;
    }
}
