package com.ssafy.auction_service.domain.auctionvariety.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.auction_service.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuctionVarietyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionVarietyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionVarietyResponse> findAllByAuctionScheduleId(int auctionScheduleId, Pageable pageable) {
        return null;
    }
}
