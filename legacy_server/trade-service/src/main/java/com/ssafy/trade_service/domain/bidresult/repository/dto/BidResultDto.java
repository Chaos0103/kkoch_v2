package com.ssafy.trade_service.domain.bidresult.repository.dto;

import com.ssafy.trade_service.api.service.order.response.orderdetail.BidResult;
import com.ssafy.trade_service.api.service.order.response.orderdetail.Variety;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BidResultDto {

    private Long id;
    private int bidPrice;
    private LocalDateTime bidDateTime;
    private Long auctionVarietyId;

    public BidResult toResponse(Variety variety) {
        return BidResult.of(id, variety, bidPrice, bidDateTime);
    }
}
