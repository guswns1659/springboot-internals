package com.titanic.springdb.repository;

import com.titanic.springdb.model.Member;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static com.titanic.springdb.connection.DataBaseConfig.H2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {
    private static final String MEMBERID = "MEMBER1";
    private static final int money = 10000;
    private static final int newMoney = 20000;

    private MemberRepositoryV1 memberRepositoryV1;

    @BeforeEach
    void setUp() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(H2.URL);
        config.setUsername(H2.USER_NAME);
        config.setPassword(H2.PASSWORD);
        config.setPoolName("Jack-Pool");

        HikariDataSource datasource = new HikariDataSource(config);

        memberRepositoryV1 = new MemberRepositoryV1(datasource);
    }

    @Test
    void crud() throws SQLException {
        // create
        Member member = new Member(MEMBERID, money);
        memberRepositoryV1.save(member);
        log.info("save member done");

        // read
        Member findMember = memberRepositoryV1.findById(MEMBERID);
        log.info("find member done / findMember = {}", findMember);

        assertThat(findMember).isEqualTo(member);

        // update
        memberRepositoryV1.update(findMember.getMemberId(), newMoney);
        Member updatedMember = memberRepositoryV1.findById(findMember.getMemberId());
        log.info("update member done / updatedMember = {}", updatedMember);

        assertThat(updatedMember.getMoney()).isEqualTo(newMoney);

        // delete
        memberRepositoryV1.delete(updatedMember.getMemberId());
        log.info("delete member done");
        assertThatThrownBy(() -> memberRepositoryV1.findById(updatedMember.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}
