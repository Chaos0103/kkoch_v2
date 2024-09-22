package com.ssafy.trade_service.domain.bidresult.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.trade_service.domain.bidresult.repository.dto.BidResultDto;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BidResultQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BidResultQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<BidResultDto> findAllByOrderId(Long orderId) {
        return null;
    }
}
