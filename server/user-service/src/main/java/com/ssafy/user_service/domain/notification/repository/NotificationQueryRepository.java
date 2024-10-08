package com.ssafy.user_service.domain.notification.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user_service.domain.notification.repository.response.SentNotificationResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.user_service.domain.membernotification.QMemberNotification.memberNotification;
import static com.ssafy.user_service.domain.notification.QNotification.notification;

@Repository
public class NotificationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public NotificationQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<SentNotificationResponse> findAllByNotificationSentDateTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return queryFactory
            .select(
                Projections.fields(
                    SentNotificationResponse.class,
                    notification.id,
                    notification.notificationCategory.as("category"),
                    notification.notificationContent.as("content"),
                    memberNotification.notification.id.count().as("sentMemberCount"),
                    notification.notificationSentDateTime.as("sentDateTime")
                )
            )
            .from(memberNotification)
            .join(memberNotification.notification, notification)
            .where(
                betweenSentDateTime(from, to)
            )
            .groupBy(memberNotification.notification.id)
            .orderBy(notification.notificationSentDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public int countByNotificationSentDateTimeBetween(LocalDateTime from, LocalDateTime to) {
        return queryFactory
            .select(notification.id)
            .from(notification)
            .where(
                betweenSentDateTime(from, to)
            )
            .fetch()
            .size();
    }

    private BooleanExpression betweenSentDateTime(LocalDateTime from, LocalDateTime to) {
        return from == null && to == null ? null : notification.notificationSentDateTime.between(from, to);
    }
}
