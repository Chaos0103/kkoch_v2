package com.kkoch.admin.api.service.trade.response;

import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import com.kkoch.admin.domain.bidresult.BidResult;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TradeDetailResponse {

    private final long tradeId;
    private final int totalPrice;
    private final LocalDateTime tradeDateTime;
    private final Boolean isPickUp;
    private final InnerAuctionSchedule auctionSchedule;
    private final List<InnerBidResult> results;

    @Builder
    private TradeDetailResponse(long tradeId, int totalPrice, LocalDateTime tradeDateTime, Boolean isPickUp, InnerAuctionSchedule auctionSchedule, List<InnerBidResult> results) {
        this.tradeId = tradeId;
        this.totalPrice = totalPrice;
        this.tradeDateTime = tradeDateTime;
        this.isPickUp = isPickUp;
        this.auctionSchedule = auctionSchedule;
        this.results = results;
    }

    public static TradeDetailResponse of(Trade trade, AuctionSchedule auctionSchedule, List<BidResult> bidResults) {
        InnerAuctionSchedule innerAuctionSchedule = new InnerAuctionSchedule(auctionSchedule.getPlantCategory(), auctionSchedule.getAuctionDateTime());

        List<InnerBidResult> innerBidResults = bidResults.stream()
            .map(InnerBidResult::of)
            .sorted()
            .collect(Collectors.toList());

        return new TradeDetailResponse(trade.getId(), trade.getTotalPrice(), trade.getTradeDateTime(), trade.isPickUp(), innerAuctionSchedule, innerBidResults);
    }

    @Getter
    public static class InnerAuctionSchedule {
        private final PlantCategory plantCategory;
        private final LocalDateTime auctionDateTime;

        @Builder
        private InnerAuctionSchedule(PlantCategory plantCategory, LocalDateTime auctionDateTime) {
            this.plantCategory = plantCategory;
            this.auctionDateTime = auctionDateTime;
        }
    }

    @Getter
    public static class InnerBidResult implements Comparable<InnerBidResult> {
        private final String varietyCode;
        private final String itemName;
        private final String varietyName;
        private final Grade grade;
        private final int plantCount;
        private final int bidPrice;
        private final LocalDateTime bidDateTime;

        @Builder
        private InnerBidResult(String varietyCode, String itemName, String varietyName, Grade grade, int plantCount, int bidPrice, LocalDateTime bidDateTime) {
            this.varietyCode = varietyCode;
            this.itemName = itemName;
            this.varietyName = varietyName;
            this.grade = grade;
            this.plantCount = plantCount;
            this.bidPrice = bidPrice;
            this.bidDateTime = bidDateTime;
        }

        public static InnerBidResult of(BidResult bidResult) {
            AuctionVariety auctionVariety = bidResult.getAuctionVariety();
            Variety variety = auctionVariety.getVariety();
            return InnerBidResult.builder()
                .varietyCode(variety.getCode())
                .itemName(variety.getItemName())
                .varietyName(variety.getVarietyName())
                .grade(auctionVariety.getAuctionVarietyInfo().getGrade())
                .plantCount(auctionVariety.getAuctionVarietyInfo().getPlantCount())
                .bidPrice(bidResult.getBidPrice())
                .bidDateTime(bidResult.getBidDateTime())
                .build();
        }

        @Override
        public int compareTo(InnerBidResult o) {
            return bidDateTime.compareTo(o.bidDateTime) * -1;
        }
    }
}
