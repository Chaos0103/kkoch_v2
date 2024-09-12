package com.ssafy.auction_service.domain.auctionschedule.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.auction_service.domain.auctionschedule.repository.dto.AuctionScheduleSearchCond;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuctionScheduleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionScheduleQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionScheduleResponse> findByCond(AuctionScheduleSearchCond cond) {
        return null;
    }
}
