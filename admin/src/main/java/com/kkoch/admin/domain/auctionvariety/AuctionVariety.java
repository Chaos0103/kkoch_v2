package com.kkoch.admin.domain.auctionvariety;

import com.kkoch.admin.domain.BaseEntity;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.bidresult.BidResult;
import com.kkoch.admin.domain.variety.Variety;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionVariety extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_variety_id")
    private Long id;

    @Column(nullable = false, updatable = false, columnDefinition = "char(5)", length = 10)
    private String auctionNumber;

    @Embedded
    private AuctionVarietyInfo auctionVarietyInfo;

    @Embedded
    private ShippingInfo shippingInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variety_code")
    private Variety variety;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_schedule_id")
    private AuctionSchedule auctionSchedule;

    @Builder
    private AuctionVariety(boolean isDeleted, int createdBy, int lastModifiedBy, String auctionNumber, AuctionVarietyInfo auctionVarietyInfo, ShippingInfo shippingInfo, Variety variety, AuctionSchedule auctionSchedule) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.auctionNumber = auctionNumber;
        this.auctionVarietyInfo = auctionVarietyInfo;
        this.shippingInfo = shippingInfo;
        this.variety = variety;
        this.auctionSchedule = auctionSchedule;
    }

    public static AuctionVariety of(boolean isDeleted, int createdBy, int lastModifiedBy, String auctionNumber, AuctionVarietyInfo auctionVarietyInfo, ShippingInfo shippingInfo, Variety variety, AuctionSchedule auctionSchedule) {
        return new AuctionVariety(isDeleted, createdBy, lastModifiedBy, auctionNumber, auctionVarietyInfo, shippingInfo, variety, auctionSchedule);
    }

    public static AuctionVariety create(String auctionNumber, AuctionVarietyInfo auctionVarietyInfo, ShippingInfo shippingInfo, Variety variety, AuctionSchedule auctionSchedule, Admin admin) {
        return of(false, admin.getId(), admin.getId(), auctionNumber, auctionVarietyInfo, shippingInfo, variety, auctionSchedule);
    }

    public int getAuctionScheduleId() {
        return auctionSchedule.getId();
    }
}
