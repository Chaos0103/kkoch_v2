package com.ssafy.trade_service.domain.auctionreservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.trade_service.domain.auctionreservation.repository.response.AuctionReservationResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuctionReservationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionReservationQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionReservationResponse> findAllByAuctionScheduleId(Long memberId, int auctionScheduleId) {
        return null;
    }
}
