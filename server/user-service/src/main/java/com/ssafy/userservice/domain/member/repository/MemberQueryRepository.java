package com.ssafy.userservice.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.userservice.domain.member.repository.response.MemberDisplayInfoDto;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ssafy.userservice.domain.member.QMember.member;

@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<MemberDisplayInfoDto> findMemberDisplayInfoByMemberKey(String memberKey) {
        MemberDisplayInfoDto content = queryFactory
            .select(
                Projections.fields(
                    MemberDisplayInfoDto.class,
                    member.email,
                    member.name,
                    member.tel
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

    public Optional<Long> findMemberIdByMemberKey(String memberKey) {
        Long memberId = queryFactory
            .select(member.id)
            .from(member)
            .where(
                member.isDeleted.isFalse(),
                member.specificInfo.memberKey.eq(memberKey)
            )
            .fetchFirst();
        return Optional.ofNullable(memberId);
    }
}
