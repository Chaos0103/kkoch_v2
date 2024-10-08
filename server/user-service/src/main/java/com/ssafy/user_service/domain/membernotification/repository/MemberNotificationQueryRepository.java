package com.ssafy.user_service.domain.membernotification.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user_service.domain.membernotification.repository.response.NotificationResponse;
import com.ssafy.user_service.domain.notification.NotificationCategory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.user_service.domain.membernotification.QMemberNotification.memberNotification;

@Repository
public class MemberNotificationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberNotificationQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<NotificationResponse> findAllByMemberKeyAndCond(String memberKey, NotificationCategory category, Pageable pageable) {
        return queryFactory
            .select(
                Projections.fields(
                    NotificationResponse.class,
                    memberNotification.id,
                    memberNotification.notification.notificationCategory.as("category"),
                    memberNotification.notification.notificationContent.as("content"),
                    memberNotification.isOpened,
                    memberNotification.notification.createdDateTime.as("notificationDateTime")
                )
            )
            .from(memberNotification)
            .where(
                isNotDeleted(),
                eqMemberKey(memberKey),
                eqNotificationCategory(category)
            )
            .orderBy(memberNotification.createdDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public int countByMemberKeyAndCond(String memberKey, NotificationCategory category) {
        return queryFactory
            .select(memberNotification.id)
            .from(memberNotification)
            .where(
                isNotDeleted(),
                eqMemberKey(memberKey),
                eqNotificationCategory(category)
            )
            .fetch()
            .size();
    }

    private static BooleanExpression isNotDeleted() {
        return memberNotification.isDeleted.isFalse();
    }

    private static BooleanExpression eqMemberKey(String memberKey) {
        return memberNotification.member.specificInfo.memberKey.eq(memberKey);
    }

    private BooleanExpression eqNotificationCategory(NotificationCategory category) {
        return category == null ? null : memberNotification.notification.notificationCategory.eq(category);
    }
}
