package com.ssafy.trade_service.domain.order.repository;

import com.ssafy.trade_service.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    String NO_SUCH_ORDER = "등록되지 않은 주문입니다.";
}
