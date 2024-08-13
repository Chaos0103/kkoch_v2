package com.ssafy.user_service.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user_service.domain.member.repository.response.MemberInfoResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ssafy.user_service.domain.member.QMember.member;

@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<MemberInfoResponse> findByMemberKey(String memberKey) {
        MemberInfoResponse content = queryFactory
            .select(
                Projections.fields(
                    MemberInfoResponse.class,
                    member.email,
                    member.name,
                    member.tel,
                    member.userAdditionalInfo.businessNumber
                )
            )
            .from(member)
            .where(
                member.isDeleted.isFalse(),
                member.specificInfo.memberKey.eq(memberKey)
            )
            .fetchFirst();
        return Optional.ofNullable(content);
    }
}
