package com.kkoch.user.domain.member.repository;

import com.kkoch.user.domain.member.repository.response.MemberInfoResponse;
import com.kkoch.user.domain.member.repository.response.MemberResponseForAdmin;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.kkoch.user.domain.member.QMember.member;

@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<MemberInfoResponse> findMemberInfoByMemberKey(String memberKey) {
        MemberInfoResponse content = queryFactory
            .select(
                Projections.fields(
                    MemberInfoResponse.class,
                    member.email,
                    member.name,
                    member.tel,
                    member.businessNumber
                )
            )
            .from(member)
            .where(
                isNotDeleted(),
                member.memberKey.eq(memberKey)
            )
            .fetchFirst();
        return Optional.ofNullable(content);
    }

    public Boolean existByEmail(String email) {
        Integer content = queryFactory
            .selectOne()
            .from(member)
            .where(member.email.eq(email))
            .fetchFirst();

        return content != null;
    }

    public List<MemberResponseForAdmin> findAllUser() {
        return queryFactory
            .select(
                Projections.fields(
                    MemberResponseForAdmin.class,
                    member.id.as("memberId"),
                    member.email,
                    member.name,
                    member.tel,
                    member.businessNumber,
                    member.point,
                    member.isDeleted
                )
            )
            .from(member)
            .fetch();
    }

    private static BooleanExpression isNotDeleted() {
        return member.isDeleted.isFalse();
    }
}

