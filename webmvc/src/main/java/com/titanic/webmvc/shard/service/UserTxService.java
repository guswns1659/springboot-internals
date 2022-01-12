package com.titanic.webmvc.shard.service;

import com.titanic.webmvc.shard.Shard;
import com.titanic.webmvc.shard.entity.UserTx;
import com.titanic.webmvc.shard.repository.UserTxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTxService {

    private final UserTxRepository userTxRepository;

    @Shard
    public String save(Long userId, String type) {
        UserTx userTx = UserTx.builder()
                .id(userId)
                .type(type)
                .build();

        return userTxRepository.save(userTx).toString();
    }
}
