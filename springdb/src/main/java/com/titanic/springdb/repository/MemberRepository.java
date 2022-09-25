package com.titanic.springdb.repository;

import com.titanic.springdb.model.Member;

public interface MemberRepository {
    void save(Member member);
    Member findById(String id);
    void update(String memberId, int money);
    int delete(String memberId);
}
