package com.ssafy.trade_service.domain.auctionstatistics.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.trade_service.domain.auctionstatistics.repository.cond.AuctionStatisticsSearchCond;
import com.ssafy.trade_service.domain.auctionstatistics.repository.response.AuctionStatisticsResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuctionStatisticsQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionStatisticsQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionStatisticsResponse> findAllByCond(String varietyCode, AuctionStatisticsSearchCond cond) {
        return null;
    }
}
