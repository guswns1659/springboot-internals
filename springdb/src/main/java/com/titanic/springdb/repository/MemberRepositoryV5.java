package com.titanic.springdb.repository;

import com.titanic.springdb.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class MemberRepositoryV5 implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryV5(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Member member) {
        String sql = "insert into member (member_id, money) values (?, ?)";
        jdbcTemplate.update(sql, member.getMemberId(), member.getMoney());
    }

    @Override
    public Member findById(String id) {
        String sql = "select * from member where member_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getString("member_id"));
            member.setMoney(rs.getInt("money"));
            return member;
        }, id);
    }

    @Override
    public void update(String memberId, int money) {
        String sql = "update member set money = ? where member_id = ?";
        jdbcTemplate.update(sql, money, memberId);
    }

    @Override
    public int delete(String memberId) {
        String sql = "delete from member where member_id = ?";
        return jdbcTemplate.update(sql, memberId);
    }
}
