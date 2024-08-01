package com.kkoch.admin.domain.trade.repository.response;

import com.kkoch.admin.domain.trade.Trade;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TradeResponse {

    private final long tradeId;
    private final int totalPrice;
    private final long bidResultCount;
    private final Boolean isPickUp;
    private final LocalDateTime tradeDateTime;
    private final LocalDateTime auctionDateTime;

    @Builder
    private TradeResponse(long tradeId, int totalPrice, long bidResultCount, Boolean isPickUp, LocalDateTime tradeDateTime, LocalDateTime auctionDateTime) {
        this.tradeId = tradeId;
        this.totalPrice = totalPrice;
        this.bidResultCount = bidResultCount;
        this.isPickUp = isPickUp;
        this.tradeDateTime = tradeDateTime;
        this.auctionDateTime = auctionDateTime;
    }

    public static TradeResponse of(Trade trade, long bidResultCount, LocalDateTime auctionDateTime) {
        return new TradeResponse(trade.getId(), trade.getTotalPrice(), bidResultCount, trade.isPickUp(), auctionDateTime, trade.getTradeDateTime());
    }
}
