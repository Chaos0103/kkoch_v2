package com.kkoch.admin.domain.bidresult;

import com.kkoch.admin.domain.TimeBaseEntity;
import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import com.kkoch.admin.domain.trade.Trade;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidResult extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_result_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private int bidPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime bidDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_variety_id")
    private AuctionVariety auctionVariety;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "trade_id")
    private Trade trade;

    @Builder
    private BidResult(boolean isDeleted, int bidPrice, LocalDateTime bidDateTime, AuctionVariety auctionVariety, Trade trade) {
        super(isDeleted);
        this.bidPrice = bidPrice;
        this.bidDateTime = bidDateTime;
        this.auctionVariety = auctionVariety;
        this.trade = trade;
    }

    public static BidResult of(boolean isDeleted, int bidPrice, LocalDateTime bidDateTime, AuctionVariety auctionVariety, Trade trade) {
        return new BidResult(isDeleted, bidPrice, bidDateTime, auctionVariety, trade);
    }

    public static BidResult create(int bidPrice, LocalDateTime bidDateTime, AuctionVariety auctionVariety, Trade trade) {
        trade.addPrice(bidPrice);
        return of(false, bidPrice, bidDateTime, auctionVariety, trade);
    }
}
