package com.ssafy.trade_service.domain.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.trade_service.domain.order.repository.dto.OrderDetailDto;
import com.ssafy.trade_service.domain.order.repository.response.OrderResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.trade_service.domain.order.QOrder.order;

@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<OrderResponse> findAllByMemberId(Long memberId, Pageable pageable) {
        return queryFactory
            .select(
                Projections.fields(
                    OrderResponse.class,
                    order.id,
                    order.orderStatus,
                    order.totalPrice,
                    order.pickUp.isPickUp,
                    order.pickUp.pickUpDateTime,
                    order.bidResults.size().as("orderCount")
                )
            )
            .from(order)
            .where(
                order.isDeleted.isFalse(),
                order.memberId.eq(memberId)
            )
            .orderBy(order.createdDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public int countByMemberId(Long memberId) {
        return queryFactory
            .select(order.id)
            .from(order)
            .where(
                order.isDeleted.isFalse(),
                order.memberId.eq(memberId)
            )
            .fetch()
            .size();
    }

    public OrderDetailDto findById(Long orderId) {
        return null;
    }
}
