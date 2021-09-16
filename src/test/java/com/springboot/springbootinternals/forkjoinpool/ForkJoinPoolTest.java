package com.springboot.springbootinternals.forkjoinpool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ForkJoinPool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ForkJoinPoolTest {

    @Test
    @DisplayName("ForkJoinPool Tutorial")
    public void tutorial() {
        // given
        int processors = Runtime.getRuntime().availableProcessors();

        assertThat(processors).isGreaterThan(0);

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        assertThat(forkJoinPool.getActiveThreadCount()).isEqualTo(0);

        NewTask t = new NewTask(400L);

        forkJoinPool.invoke(t);

        assertThat(forkJoinPool.getActiveThreadCount()).isGreaterThan(1);
        assertThat(forkJoinPool.getPoolSize()).isGreaterThanOrEqualTo(3);
    }
}
