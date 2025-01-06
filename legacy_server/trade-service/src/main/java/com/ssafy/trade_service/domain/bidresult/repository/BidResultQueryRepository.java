package com.ssafy.trade_service.domain.bidresult.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.trade_service.domain.bidresult.repository.dto.BidResultDto;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.trade_service.domain.bidresult.QBidResult.bidResult;

@Repository
public class BidResultQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BidResultQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<BidResultDto> findAllByOrderId(Long orderId) {
        return queryFactory
            .select(
                Projections.fields(
                    BidResultDto.class,
                    bidResult.id,
                    bidResult.bidPrice,
                    bidResult.bidDateTime,
                    bidResult.auctionVarietyId
                )
            )
            .from(bidResult)
            .where(
                bidResult.isDeleted.isFalse(),
                bidResult.order.id.eq(orderId)
            )
            .orderBy(bidResult.bidDateTime.asc())
            .fetch();
    }
}
