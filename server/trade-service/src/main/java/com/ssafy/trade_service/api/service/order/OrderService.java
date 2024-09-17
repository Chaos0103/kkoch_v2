package com.ssafy.trade_service.api.service.order;

import com.ssafy.trade_service.api.service.order.response.OrderCreateResponse;
import com.ssafy.trade_service.domain.bidinfo.repository.BidRepository;
import com.ssafy.trade_service.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BidRepository bidRepository;

    public OrderCreateResponse createOrder(Long memberId) {
        return null;
    }
}
