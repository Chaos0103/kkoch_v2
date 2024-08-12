package com.ssafy.user_service.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user_service.domain.member.repository.response.MemberInfoResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<MemberInfoResponse> findByMemberKey(String memberKey) {
        return Optional.empty();
    }
}
