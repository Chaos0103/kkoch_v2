package com.ssafy.trade_service.api.service.order;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.PageResponse;
import com.ssafy.trade_service.api.client.AuctionServiceClient;
import com.ssafy.trade_service.api.client.MemberServiceClient;
import com.ssafy.trade_service.api.client.response.AuctionVarietyResponse;
import com.ssafy.trade_service.api.client.response.MemberIdResponse;
import com.ssafy.trade_service.api.service.order.response.OrderDetailResponse;
import com.ssafy.trade_service.api.service.order.vo.AuctionVarieties;
import com.ssafy.trade_service.api.service.order.vo.BidResults;
import com.ssafy.trade_service.common.util.PageUtils;
import com.ssafy.trade_service.domain.bidresult.repository.BidResultQueryRepository;
import com.ssafy.trade_service.domain.bidresult.repository.dto.BidResultDto;
import com.ssafy.trade_service.domain.order.repository.OrderQueryRepository;
import com.ssafy.trade_service.domain.order.repository.dto.OrderDetailDto;
import com.ssafy.trade_service.domain.order.repository.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ssafy.trade_service.domain.order.repository.OrderRepository.NO_SUCH_ORDER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderQueryRepository orderQueryRepository;
    private final BidResultQueryRepository bidResultQueryRepository;
    private final MemberServiceClient memberServiceClient;
    private final AuctionServiceClient auctionServiceClient;

    public PageResponse<OrderResponse> searchOrders(int pageNumber) {
        Long memberId = getMemberId();

        Pageable pageable = PageUtils.of(pageNumber);

        List<OrderResponse> content = orderQueryRepository.findAllByMemberId(memberId, pageable);
        int total = orderQueryRepository.countByMemberId(memberId);

        return PageResponse.create(content, pageable, total);
    }

    public OrderDetailResponse searchOrder(Long orderId) {
        //find order
        OrderDetailDto order = orderQueryRepository.findById(orderId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ORDER));

        //find all bid result by order id
        BidResults bidResults = findBidResultsByOrderId(orderId);

        //get auction variety id list
        List<Long> auctionVarietyIds = bidResults.getAuctionVarietyIdList();

        //find all auction variety by auction variety id
        AuctionVarieties auctionVarieties = findAuctionVarietiesByIdIn(auctionVarietyIds);

        //generate response object
        return order.toResponse(bidResults, auctionVarieties);
    }

    private BidResults findBidResultsByOrderId(Long orderId) {
        List<BidResultDto> bidResults = bidResultQueryRepository.findAllByOrderId(orderId);
        return BidResults.of(bidResults);
    }

    private AuctionVarieties findAuctionVarietiesByIdIn(List<Long> auctionVarietyIds) {
        ApiResponse<List<AuctionVarietyResponse>> response = auctionServiceClient.findAllByIdIn(auctionVarietyIds);
        return AuctionVarieties.of(response.getData());
    }

    private Long getMemberId() {
        ApiResponse<MemberIdResponse> response = memberServiceClient.searchMemberId();
        return response.getData().getMemberId();
    }
}
