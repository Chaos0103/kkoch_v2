package com.ssafy.trade_service.domain.bidresult;

import com.ssafy.trade_service.domain.TimeBaseEntity;
import com.ssafy.trade_service.domain.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidResult extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private Order order;

    @Column(nullable = false, updatable = false)
    private Long auctionVarietyId;

    @Column(nullable = false, updatable = false, columnDefinition = "char(8)", length = 8)
    private String varietyCode;

    @Column(nullable = false, updatable = false, length = 20)
    private String plantGrade;

    @Column(nullable = false, updatable = false)
    private int plantCount;

    @Column(nullable = false, updatable = false)
    private int bidPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime bidDateTime;

    @Builder
    private BidResult(boolean isDeleted, Order order, Long auctionVarietyId, String varietyCode, String plantGrade, int plantCount, int bidPrice, LocalDateTime bidDateTime) {
        super(isDeleted);
        this.order = order;
        this.auctionVarietyId = auctionVarietyId;
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.bidPrice = bidPrice;
        this.bidDateTime = bidDateTime;
    }

    public static BidResult of(boolean isDeleted, Order order, Long auctionVarietyId, String varietyCode, String plantGrade, int plantCount, int bidPrice, LocalDateTime bidDateTime) {
        return new BidResult(isDeleted, order, auctionVarietyId, varietyCode, plantGrade, plantCount, bidPrice, bidDateTime);
    }

    public static BidResult create(Order order, Long auctionVarietyId, String varietyCode, String plantGrade, int plantCount, int bidPrice, LocalDateTime bidDateTime) {
        BidResult bidResult = of(false, order, auctionVarietyId, varietyCode, plantGrade, plantCount, bidPrice, bidDateTime);
        order.addBidResult(bidResult);
        return bidResult;
    }
}
