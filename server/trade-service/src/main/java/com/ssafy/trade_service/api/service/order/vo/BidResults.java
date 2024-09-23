package com.ssafy.trade_service.api.service.order.vo;

import com.ssafy.trade_service.api.service.order.response.orderdetail.BidResult;
import com.ssafy.trade_service.api.service.order.response.orderdetail.Variety;
import com.ssafy.trade_service.domain.bidresult.repository.dto.BidResultDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BidResults {

    private final List<BidResultDto> values;

    private BidResults(List<BidResultDto> values) {
        this.values = values;
    }

    public static BidResults of(List<BidResultDto> bidResults) {
        return new BidResults(bidResults);
    }

    public List<Long> getAuctionVarietyIdList() {
        return getBidResults().stream()
            .map(BidResultDto::getAuctionVarietyId)
            .toList();
    }

    public List<BidResult> generateBidResultList(AuctionVarieties auctionVarieties) {
        Map<Long, Variety> varietyMap = auctionVarieties.generateVarietyMap();
        return getBidResults().stream()
            .map(result -> result.toResponse(varietyMap.get(result.getId())))
            .toList();
    }

    public List<BidResultDto> getBidResults() {
        return new ArrayList<>(values);
    }
}
