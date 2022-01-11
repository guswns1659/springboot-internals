package com.titanic.webmvc.shard;


import com.titanic.webmvc.shard.entity.UserTx;
import com.titanic.webmvc.shard.repository.UserTxRepository;
import com.titanic.webmvc.shard.service.UserTxService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShardTest {

    @InjectMocks
    private UserTxService userTxService;

    @Mock
    private UserTxRepository userTxRepository;

    /** TestCase
     * 1. Choosing correct shard by userId success
     * 2. Choosing default shard when UserContext not exist
     * 3.
     */

    // TODO(jack.comeback) : aspect가 적용된 테스트를 하려면?
    @Test
    public void userID기반_정확한_shardDB_선택_성공() {
        // given
        when(userTxRepository.save(any(UserTx.class))).thenReturn(UserTx.builder().id(1L).type("use").build());

        // when
        userTxService.save(1L, "use");
        String shardNum = UserContextHolder.getShardDbName().orElse("");

        // then
        assertThat(shardNum).isEqualTo("SHARD-00");
    }
}
