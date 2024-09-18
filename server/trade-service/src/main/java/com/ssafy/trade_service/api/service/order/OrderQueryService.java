package com.ssafy.trade_service.api.service.order;

import com.ssafy.trade_service.api.PageResponse;
import com.ssafy.trade_service.domain.order.repository.OrderQueryRepository;
import com.ssafy.trade_service.domain.order.repository.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderQueryRepository orderQueryRepository;

    public PageResponse<OrderResponse> searchOrders(int pageNumber) {
        return null;
    }
}
