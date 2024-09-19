package com.ssafy.trade_service.api.service.order;

import com.ssafy.trade_service.api.service.order.response.OrderCreateResponse;
import com.ssafy.trade_service.api.service.order.response.OrderPickUpResponse;
import com.ssafy.trade_service.common.exception.AppException;
import com.ssafy.trade_service.domain.bidinfo.Bid;
import com.ssafy.trade_service.domain.bidinfo.repository.BidRepository;
import com.ssafy.trade_service.domain.order.Order;
import com.ssafy.trade_service.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.ssafy.trade_service.domain.bidinfo.repository.BidRepository.NO_SUCH_BID;
import static com.ssafy.trade_service.domain.order.repository.OrderRepository.NO_SUCH_ORDER;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private static final String DOES_NOT_PAY = "결제되지 않은 주문입니다.";

    private final OrderRepository orderRepository;
    private final BidRepository bidRepository;

    public OrderCreateResponse createOrder(Long memberId) {
        Bid bid = findBidByMemberId(memberId);

        Order order = bid.toOrder();
        Order savedOrder = orderRepository.save(order);

        bidRepository.deleteById(memberId);

        return OrderCreateResponse.of(savedOrder);
    }

    public OrderPickUpResponse pickUp(Long orderId, LocalDateTime current) {
        Order order = findOrderById(orderId);

        if (order.cannotPickUp()) {
            throw new AppException(DOES_NOT_PAY);
        }

        order.pickUp(current);

        return OrderPickUpResponse.of(order);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ORDER));
    }

    private Bid findBidByMemberId(Long memberId) {
        return bidRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_BID));
    }
}
