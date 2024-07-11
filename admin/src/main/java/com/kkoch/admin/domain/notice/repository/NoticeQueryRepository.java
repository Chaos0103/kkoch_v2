package com.kkoch.admin.domain.notice.repository;

import com.kkoch.admin.domain.notice.repository.dto.NoticeSearchCond;
import com.kkoch.admin.domain.notice.repository.response.NoticeDetailResponse;
import com.kkoch.admin.domain.notice.repository.response.NoticeResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.kkoch.admin.domain.notice.QNotice.notice;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class NoticeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public NoticeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Integer> findAllIdByCond(NoticeSearchCond cond, Pageable pageable) {
        return queryFactory
            .select(notice.id)
            .from(notice)
            .where(
                isNotDeleted(),
                titleContains(cond.getKeyword())
            )
            .orderBy(notice.createdDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public List<NoticeResponse> findAllByIdIn(List<Integer> ids) {
        return queryFactory
            .select(
                Projections.fields(
                    NoticeResponse.class,
                    notice.id.as("noticeId"),
                    notice.title,
                    notice.content,
                    notice.createdDateTime
                )
            )
            .from(notice)
            .where(notice.id.in(ids))
            .orderBy(notice.createdDateTime.desc())
            .fetch();
    }

    public int countByCond(NoticeSearchCond cond) {
        return queryFactory
            .select(notice.id)
            .from(notice)
            .where(
                isNotDeleted(),
                notice.title.contains(cond.getKeyword())
            )
            .fetch()
            .size();
    }

    public Optional<NoticeDetailResponse> findById(int id) {
        NoticeDetailResponse content = queryFactory
            .select(
                Projections.fields(
                    NoticeDetailResponse.class,
                    Expressions.asNumber(id).as("noticeId"),
                    notice.title,
                    notice.content,
                    notice.createdDateTime
                )
            )
            .from(notice)
            .where(
                isNotDeleted(),
                notice.id.eq(id)
            )
            .fetchFirst();
        return Optional.ofNullable(content);
    }

    private static BooleanExpression isNotDeleted() {
        return notice.isDeleted.isFalse();
    }

    private BooleanExpression titleContains(String keyword) {
        return hasText(keyword) ? notice.title.contains(keyword) : null;
    }
}
