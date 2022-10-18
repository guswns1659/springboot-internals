package com.titanic.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    private PlatformTransactionManager txManager;

    @TestConfiguration
    static class config {
        @Bean
        public PlatformTransactionManager txManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit() {
        log.info("tx 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("tx commit");
        txManager.commit(status);
        log.info("tx commit completed");
    }

    @Test
    void rollback() {
        log.info("tx 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("tx rollback");
        txManager.rollback(status);
        log.info("tx rollback completed");
    }

    @Test
    void double_commit() {
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new
                DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋"); txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new
                DefaultTransactionAttribute());
        log.info("트랜잭션2 커밋");
        txManager.commit(tx2);
    }


    @Test
    void double_commit_rollback() {
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new
                DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋"); txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new
                DefaultTransactionAttribute());
        log.info("트랜잭션2 롤백");
        txManager.rollback(tx2);
    }

    @Test
    void inner_commit() {
        TransactionStatus externalTx = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("externalTx isNewTransaction = {}", externalTx.isNewTransaction());

        TransactionStatus innerTx = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("innerTx isNewTransaction = {}", innerTx.isNewTransaction());

        txManager.commit(innerTx);

        txManager.commit(externalTx);
    }

    @Test
    void outer_rollback() {
        TransactionStatus outerTx = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outerTx isNewTx = {}", outerTx.isNewTransaction());

        TransactionStatus innerTx = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("innerTx isNewTx = {}", innerTx.isNewTransaction());

        txManager.commit(innerTx);
        txManager.rollback(outerTx);
    }

    @Test
    void inner_rollback() {
        TransactionStatus outerTx = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outerTx isNewTx = {}", outerTx.isNewTransaction());

        TransactionStatus innerTx = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("innerTx isNewTx = {}", innerTx.isNewTransaction());

        txManager.rollback(innerTx);

        DefaultTransactionStatus defaultInnerTx = (DefaultTransactionStatus) innerTx;
        DefaultTransactionStatus defaultOuterTx = (DefaultTransactionStatus) outerTx;

        // tx commit or rollback 후에는 셋팅값을 초기화시키는 것 같아서 여기서 검증
        assertThat(defaultInnerTx.isGlobalRollbackOnly()).isTrue();
        assertThat(defaultOuterTx.isGlobalRollbackOnly()).isTrue();
        assertThatThrownBy(() -> txManager.commit(outerTx))
                .isInstanceOf(UnexpectedRollbackException.class);
    }

    @Test
    void inner_rollback_requires_new() {
        TransactionStatus outerTx = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outerTx isNewTx = {}", outerTx.isNewTransaction());

        DefaultTransactionAttribute requiresNew = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus innerTx = txManager.getTransaction(requiresNew);
        log.info("innerTx isNewTx = {}", innerTx.isNewTransaction());

        txManager.rollback(innerTx);
        txManager.commit(outerTx);
    }
}
