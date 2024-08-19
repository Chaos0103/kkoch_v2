package com.ssafy.board_service.domain.notice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.board_service.domain.notice.repository.response.NoticeResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class NoticeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public NoticeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<NoticeResponse> findNotFixedAllByNoticeTitleContaining(String keyword, LocalDateTime currentDateTime, Pageable pageable) {
        return null;
    }

    public List<NoticeResponse> findFixedAll(LocalDateTime currentDateTime) {
        return null;
    }

    public int countNotFixedByNoticeTitleContaining() {
        return 0;
    }
}
