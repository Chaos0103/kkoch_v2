package com.ssafy.trade_service.api.service.order.vo;

import com.ssafy.trade_service.api.client.response.AuctionVarietyResponse;
import com.ssafy.trade_service.api.service.order.response.orderdetail.Variety;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuctionVarieties {

    private final List<AuctionVarietyResponse> values;

    private AuctionVarieties(List<AuctionVarietyResponse> values) {
        this.values = values;
    }

    public static AuctionVarieties of(List<AuctionVarietyResponse> auctionVarieties) {
        return new AuctionVarieties(auctionVarieties);
    }

    public Map<Long, Variety> generateVarietyMap() {
        return getAuctionVarieties().stream()
            .collect(Collectors.toMap(AuctionVarietyResponse::getAuctionVarietyId, AuctionVarietyResponse::toVariety, (a, b) -> b));
    }

    public List<AuctionVarietyResponse> getAuctionVarieties() {
        return new ArrayList<>(values);
    }
}
