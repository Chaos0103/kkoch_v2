package com.ssafy.trade_service.api.service.order.response;

import com.ssafy.trade_service.api.service.order.response.orderdetail.BidResult;
import com.ssafy.trade_service.api.service.order.vo.AuctionVarieties;
import com.ssafy.trade_service.api.service.order.vo.BidResults;
import com.ssafy.trade_service.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderDetailResponse {

    private Long id;
    private OrderStatus orderStatus;
    private int totalPrice;
    private Boolean isPickUp;
    private LocalDateTime pickUpDateTime;
    private List<BidResult> bidResults;

    @Builder
    private OrderDetailResponse(Long id, OrderStatus orderStatus, int totalPrice, Boolean isPickUp, LocalDateTime pickUpDateTime, List<BidResult> bidResults) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.isPickUp = isPickUp;
        this.pickUpDateTime = pickUpDateTime;
        this.bidResults = bidResults;
    }

    public static OrderDetailResponse of(Long id, OrderStatus orderStatus, int totalPrice, Boolean isPickUp, LocalDateTime pickUpDateTime, BidResults bidResults, AuctionVarieties auctionVarieties) {
        return new OrderDetailResponse(id, orderStatus, totalPrice, isPickUp, pickUpDateTime, bidResults.generateBidResultList(auctionVarieties));
    }
}
