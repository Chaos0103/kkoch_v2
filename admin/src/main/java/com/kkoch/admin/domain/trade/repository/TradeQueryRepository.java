package com.kkoch.admin.domain.trade.repository;

import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.response.TradeResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkoch.admin.domain.trade.QTrade.trade;

@Repository
public class TradeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public TradeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Long> findAllIdByCond(String memberKey, Pageable pageable) {
        return queryFactory
            .select(trade.id)
            .from(trade)
            .where(
                trade.isDeleted.isFalse(),
                trade.memberKey.eq(memberKey)
            )
            .orderBy(trade.createdDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public List<Trade> findAllByIdIn(List<Long> ids) {
        return queryFactory
            .select(trade)
            .from(trade)
            .where(trade.id.in(ids))
            .orderBy(trade.createdDateTime.desc())
            .fetch();
    }

    public int countByCond(String memberKey) {
        return queryFactory
            .select(trade.id)
            .from(trade)
            .where(
                trade.isDeleted.isFalse(),
                trade.memberKey.eq(memberKey)
            )
            .fetch()
            .size();
    }
}
