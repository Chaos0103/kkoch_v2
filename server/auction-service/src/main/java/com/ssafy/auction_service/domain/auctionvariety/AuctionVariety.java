package com.ssafy.auction_service.domain.auctionvariety;

import com.ssafy.auction_service.domain.BaseEntity;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.variety.Variety;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionVariety extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_variety_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_schedule_id")
    private AuctionSchedule auctionSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variety_code")
    private Variety variety;

    @Column(nullable = false, updatable = false, columnDefinition = "char(5)", length = 5)
    private String listingNumber;

    @Embedded
    private AuctionPlant auctionPlant;

    @Embedded
    private Shipment shipment;

    @Builder
    private AuctionVariety(boolean isDeleted, Long createdBy, Long lastModifiedBy, AuctionSchedule auctionSchedule, Variety variety, String listingNumber, AuctionPlant auctionPlant, Shipment shipment) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.auctionSchedule = auctionSchedule;
        this.variety = variety;
        this.listingNumber = listingNumber;
        this.auctionPlant = auctionPlant;
        this.shipment = shipment;
    }

    public static AuctionVariety of(boolean isDeleted, Long createdBy, Long lastModifiedBy, AuctionSchedule auctionSchedule, Variety variety, String listingNumber, AuctionPlant auctionPlant, Shipment shipment) {
        return new AuctionVariety(isDeleted, createdBy, lastModifiedBy, auctionSchedule, variety, listingNumber, auctionPlant, shipment);
    }

    public static AuctionVariety create(AuctionSchedule auctionSchedule, Variety variety, String listingNumber, AuctionPlant auctionPlant, Shipment shipment) {
        return of(false, null, null, auctionSchedule, variety, listingNumber, auctionPlant, shipment);
    }
}
