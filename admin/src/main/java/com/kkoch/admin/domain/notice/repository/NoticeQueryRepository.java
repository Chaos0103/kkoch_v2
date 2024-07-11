package com.kkoch.admin.domain.notice.repository;

import com.kkoch.admin.api.controller.notice.response.NoticeResponse;
import com.kkoch.admin.domain.notice.repository.dto.NoticeSearchCond;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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

    public List<NoticeResponse> getAllNotices() {
        return null;
    }

    public List<NoticeResponse> getNoticeByCondition(NoticeSearchCond cond, Pageable pageable) {
        return null;
    }

    public long getTotalCount(NoticeSearchCond cond) {
        return 0L;
    }

    public Optional<NoticeResponse> getNotice(Long noticeId) {
        return Optional.empty();
    }

    private BooleanExpression eqTitle(String title) {
        return hasText(title) ? notice.title.like("%" + title + "%") : null;
    }

    private BooleanExpression eqContent(String content) {
        return hasText(content) ? notice.content.like("%" + content + "%") : null;
    }
}
