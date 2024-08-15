package com.ssafy.user_service.domain.membernotification.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user_service.domain.membernotification.repository.response.NotificationResponse;
import com.ssafy.user_service.domain.notification.NotificationCategory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberNotificationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberNotificationQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<NotificationResponse> findAllByMemberKeyAndCond(String memberKey, NotificationCategory category, Pageable pageable) {
        return null;
    }
}
