package com.ssafy.board_service.domain.notice.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.board_service.domain.notice.repository.response.NoticeResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.board_service.domain.notice.QNotice.notice;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class NoticeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public NoticeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<NoticeResponse> findNotFixedAllByNoticeTitleContaining(String keyword, LocalDateTime currentDateTime, Pageable pageable) {
        return queryFactory
            .select(
                Projections.fields(
                    NoticeResponse.class,
                    notice.id,
                    notice.noticeTitle.as("title"),
                    Expressions.asBoolean(false).as("isFixed"),
                    notice.createdDateTime
                )
            )
            .from(notice)
            .where(
                isNotDeleted(),
                isNotFixed(currentDateTime),
                containsNoticeTitle(keyword)
            )
            .orderBy(notice.createdDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public List<NoticeResponse> findFixedAll(LocalDateTime currentDateTime) {
        return queryFactory
            .select(
                Projections.fields(
                    NoticeResponse.class,
                    notice.id,
                    notice.noticeTitle.as("title"),
                    Expressions.asBoolean(true).as("isFixed"),
                    notice.createdDateTime
                )
            )
            .from(notice)
            .where(
                isNotDeleted(),
                isFixed(currentDateTime)
            )
            .orderBy(notice.createdDateTime.desc())
            .fetch();
    }

    public int countNotFixedByNoticeTitleContaining(String keyword, LocalDateTime currentDateTime) {
        return queryFactory
            .select(notice.id)
            .from(notice)
            .where(
                isNotDeleted(),
                isNotFixed(currentDateTime),
                containsNoticeTitle(keyword)
            )
            .fetch()
            .size();
    }

    private BooleanExpression isNotDeleted() {
        return notice.isDeleted.isFalse();
    }

    private BooleanExpression isNotFixed(LocalDateTime currentDateTime) {
        return notice.toFixedDateTime.before(currentDateTime);
    }

    private BooleanExpression isFixed(LocalDateTime currentDateTime) {
        return isNotFixed(currentDateTime).not();
    }

    private BooleanExpression containsNoticeTitle(String keyword) {
        return hasText(keyword) ? notice.noticeTitle.contains(keyword) : null;
    }
}
