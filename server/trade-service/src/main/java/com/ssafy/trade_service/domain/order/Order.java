package com.ssafy.trade_service.domain.order;

import com.ssafy.trade_service.domain.TimeBaseEntity;
import com.ssafy.trade_service.domain.bidinfo.BidInfo;
import com.ssafy.trade_service.domain.bidresult.BidResult;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus orderStatus;

    @Column(nullable = false, updatable = false)
    private int totalPrice;

    @Embedded
    private PickUp pickUp;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BidResult> bidResults;

    @Builder
    private Order(boolean isDeleted, Long memberId, OrderStatus orderStatus, int totalPrice, PickUp pickUp, List<BidResult> bidResults) {
        super(isDeleted);
        this.memberId = memberId;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.pickUp = pickUp;
        this.bidResults = bidResults;
    }

    public static Order of(boolean isDeleted, Long memberId, OrderStatus orderStatus, int totalPrice, PickUp pickUp, List<BidResult> bidResults) {
        return new Order(isDeleted, memberId, orderStatus, totalPrice, pickUp, bidResults);
    }

    public static Order create(Long memberId, List<BidInfo> bidInfos) {
        Order order = of(false, memberId, OrderStatus.INIT, 0, PickUp.init(), new ArrayList<>());

        bidInfos.forEach(bidInfo -> bidInfo.toBidResult(order));

        return order;
    }

    public void addBidResult(BidResult bidResult) {
        bidResults.add(bidResult);
        totalPrice += bidResult.getBidPrice();
    }
}
