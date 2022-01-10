package com.titanic.webmvc.shard.repository;

import com.titanic.webmvc.shard.entity.ShardTx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShardTxRepository extends JpaRepository<ShardTx, Long> {
}
