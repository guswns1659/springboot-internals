package com.titanic.webmvc.shard.repository;

import com.titanic.webmvc.shard.entity.UserTx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTxRepository extends JpaRepository<UserTx, Long> {
}
