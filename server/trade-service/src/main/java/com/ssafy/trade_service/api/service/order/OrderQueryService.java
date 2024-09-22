package com.ssafy.trade_service.api.service.order;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.PageResponse;
import com.ssafy.trade_service.api.client.MemberServiceClient;
import com.ssafy.trade_service.api.client.response.MemberIdResponse;
import com.ssafy.trade_service.api.service.order.response.OrderDetailResponse;
import com.ssafy.trade_service.common.util.PageUtils;
import com.ssafy.trade_service.domain.order.repository.OrderQueryRepository;
import com.ssafy.trade_service.domain.order.repository.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderQueryRepository orderQueryRepository;
    private final MemberServiceClient memberServiceClient;

    public PageResponse<OrderResponse> searchOrders(int pageNumber) {
        Long memberId = getMemberId();

        Pageable pageable = PageUtils.of(pageNumber);

        List<OrderResponse> content = orderQueryRepository.findAllByMemberId(memberId, pageable);
        int total = orderQueryRepository.countByMemberId(memberId);

        return PageResponse.create(content, pageable, total);
    }

    public OrderDetailResponse searchOrder(Long orderId) {
        //find order
        //find all bid result by order id
        //get auction variety id list
        //find all auction variety by auction variety id
        //generate response object
        return null;
    }

    private Long getMemberId() {
        ApiResponse<MemberIdResponse> response = memberServiceClient.searchMemberId();
        return response.getData().getMemberId();
    }
}
