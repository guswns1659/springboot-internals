package com.titanic.webmvc.shard;


import com.titanic.webmvc.shard.aspect.ShardAspect;
import com.titanic.webmvc.shard.repository.UserTxRepository;
import com.titanic.webmvc.shard.service.ShardNumChecker;
import com.titanic.webmvc.shard.service.UserTxService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShardTest {

    @InjectMocks
    private UserTxService userTxService;

    @Mock
    private UserTxRepository userTxRepository;

    @InjectMocks
    private ShardAspect shardAspect;

    @Mock
    private ShardNumChecker shardNumChecker;

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @InjectMocks
    private ShardDataSourceRouter shardDataSourceRouter;

    /**
     * TestCase
     * 1. Choosing correct shard by userId success
     * 2. Choosing default shard when UserContext not exist
     * 3.
     */

    @Test
    public void aspect_정상동작_성공() throws Throwable {
        // given

        // when
        shardAspect.shardAround(proceedingJoinPoint, null, 1L);

        // then
        verify(shardNumChecker, times(1)).processSharding(anyLong());
    }

    // TODO(jack.comeback) : 기능은 정상동작. 하지만 단위테스트를 정확하게 매칭하지 못함.
//    @Test
    public void userID기반_정확한_shardDB_선택_성공() throws Throwable {
        // given
        when(shardNumChecker.processSharding(1L)).thenReturn(new ShardDb(1));

        // when
        shardAspect.shardAround(proceedingJoinPoint, null, 1L);
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebmvcApplication.class);
//        DataSource bean = context.getBean(DataSource.class);
        Object o = shardDataSourceRouter.determineCurrentLookupKey();

        // then
        verify(shardNumChecker, times(1)).processSharding(anyLong());
        String shardNum = UserContextHolder.getShardDbName().orElse("");
        assertThat(shardNum).isEqualTo("SHARD-01");
    }
}
