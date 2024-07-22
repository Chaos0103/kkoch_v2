package com.kkoch.admin.domain.trade;

import com.kkoch.admin.domain.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trade extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Long id;

    @Column(nullable = false, updatable = false, columnDefinition = "char(36)", length = 36)
    private String memberKey;

    @Column(nullable = false)
    private int totalPrice;

    private LocalDateTime tradeDateTime;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isPickUp;

    @Column(nullable = false, updatable = false)
    private int auctionScheduleId;

    @Builder
    private Trade(boolean isDeleted, String memberKey, int totalPrice, LocalDateTime tradeDateTime, boolean isPickUp, int auctionScheduleId) {
        super(isDeleted);
        this.memberKey = memberKey;
        this.totalPrice = totalPrice;
        this.tradeDateTime = tradeDateTime;
        this.isPickUp = isPickUp;
        this.auctionScheduleId = auctionScheduleId;
    }

    public static Trade of(boolean isDeleted, String memberKey, int totalPrice, LocalDateTime tradeDateTime, boolean isPickUp, int auctionScheduleId) {
        return new Trade(isDeleted, memberKey, totalPrice, tradeDateTime, isPickUp, auctionScheduleId);
    }

    public static Trade create(String memberKey, int auctionScheduleId) {
        return of(false, memberKey, 0, null, false, auctionScheduleId);
    }

    public void addPrice(int price) {
        totalPrice += price;
    }
}
