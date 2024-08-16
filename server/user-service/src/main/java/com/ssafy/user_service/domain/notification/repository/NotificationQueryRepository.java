package com.ssafy.user_service.domain.notification.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user_service.domain.notification.repository.response.SentNotificationResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class NotificationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public NotificationQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<SentNotificationResponse> findAllByNotificationSentDateTimeBetween(LocalDateTime searchStartDateTime, LocalDateTime searchEndDateTime, Pageable pageable) {
        return null;
    }
}
