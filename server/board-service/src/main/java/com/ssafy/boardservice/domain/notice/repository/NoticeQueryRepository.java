package com.ssafy.boardservice.domain.notice.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.boardservice.domain.notice.repository.response.NoticeDisplayResponse;
import com.ssafy.boardservice.domain.notice.repository.response.NoticeListDisplayResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ssafy.boardservice.domain.notice.QNotice.notice;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class NoticeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public NoticeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<NoticeListDisplayResponse> findPagingByTitleContainingAndNotFixed(String keyword, LocalDateTime currentDateTime, Pageable pageable) {
        return queryFactory
            .select(
                Projections.fields(
                    NoticeListDisplayResponse.class,
                    notice.id.as("noticeId"),
                    notice.title.value.as("title"),
                    Expressions.asBoolean(false).as("isFixed"),
                    notice.createdDateTime
                )
            )
            .from(notice)
            .where(
                isNotDeleted(),
                isNotFixed(currentDateTime),
                containsTitle(keyword)
            )
            .orderBy(notice.createdDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public List<NoticeListDisplayResponse> findAllByFixed(LocalDateTime currentDateTime) {
        return queryFactory
            .select(
                Projections.fields(
                    NoticeListDisplayResponse.class,
                    notice.id.as("noticeId"),
                    notice.title.value.as("title"),
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

    public int countsByTitleContainingAndNotFixed(String keyword, LocalDateTime currentDateTime) {
        return queryFactory
            .select(notice.id)
            .from(notice)
            .where(
                isNotDeleted(),
                isNotFixed(currentDateTime),
                containsTitle(keyword)
            )
            .fetch()
            .size();
    }

    public Optional<NoticeDisplayResponse> findDisplayById(int noticeId) {
        NoticeDisplayResponse content = queryFactory
            .select(
                Projections.fields(
                    NoticeDisplayResponse.class,
                    notice.id.as("noticeId"),
                    notice.title.value.as("title"),
                    notice.content,
                    notice.createdDateTime
                )
            )
            .from(notice)
            .where(
                isNotDeleted(),
                notice.id.eq(noticeId)
            )
            .fetchFirst();
        return Optional.ofNullable(content);
    }

    private BooleanExpression isNotDeleted() {
        return notice.isDeleted.isFalse();
    }

    private BooleanExpression isNotFixed(LocalDateTime currentDateTime) {
        return notice.toFixedDateTime.value.before(currentDateTime);
    }

    private BooleanExpression isFixed(LocalDateTime currentDateTime) {
        return isNotFixed(currentDateTime).not();
    }

    private BooleanExpression containsTitle(String keyword) {
        return hasText(keyword) ? notice.title.value.contains(keyword) : null;
    }
}
