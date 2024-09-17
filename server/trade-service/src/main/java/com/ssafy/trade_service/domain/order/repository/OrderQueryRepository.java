package com.ssafy.trade_service.domain.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.trade_service.domain.order.repository.response.OrderResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<OrderResponse> findAllByMemberId(Long memberId, Pageable pageable) {
        return null;
    }
}
