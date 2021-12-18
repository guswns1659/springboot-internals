package com.titanic.webmvc.forkjoinpool;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.assertThat;

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
