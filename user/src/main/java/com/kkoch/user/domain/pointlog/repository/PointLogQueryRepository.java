package com.kkoch.user.domain.pointlog.repository;

import com.kkoch.user.domain.pointlog.repository.response.PointLogResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkoch.user.domain.member.QMember.member;
import static com.kkoch.user.domain.pointlog.QPointLog.pointLog;

@Repository
public class PointLogQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PointLogQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Long> findAllIdByMemberKey(String memberKey, Pageable pageable) {
        return queryFactory
            .select(pointLog.id)
            .from(pointLog)
            .join(pointLog.member, member)
            .where(
                isNotDeleted(),
                equalMemberKey(memberKey)
            )
            .orderBy(pointLog.createdDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public List<PointLogResponse> findAllByIdIn(List<Long> ids) {
        return queryFactory
            .select(
                Projections.fields(
                    PointLogResponse.class,
                    pointLog.id.as("pointLogId"),
                    pointLog.bank,
                    pointLog.amount,
                    pointLog.status,
                    pointLog.createdDateTime.as("createdDate")
                )
            )
            .from(pointLog)
            .where(pointLog.id.in(ids))
            .orderBy(pointLog.createdDateTime.desc())
            .fetch();
    }

    public int countByMemberKey(String memberKey) {
        return queryFactory
            .select(pointLog.id)
            .from(pointLog)
            .join(pointLog.member, member)
            .where(
                isNotDeleted(),
                equalMemberKey(memberKey)
            )
            .fetch()
            .size();
    }

    private BooleanExpression isNotDeleted() {
        return pointLog.isDeleted.isFalse();
    }

    private BooleanExpression equalMemberKey(String memberKey) {
        return pointLog.member.memberKey.eq(memberKey);
    }
}
