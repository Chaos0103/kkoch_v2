package com.ssafy.trade_service.domain.auctionreservation;

import com.ssafy.trade_service.domain.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionReservation extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_reservation_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long memberId;

    @Column(nullable = false, updatable = false)
    private Integer auctionScheduleId;

    @Embedded
    private ReservationInfo reservationInfo;

    @Builder
    private AuctionReservation(boolean isDeleted, Long memberId, Integer auctionScheduleId, ReservationInfo reservationInfo) {
        super(isDeleted);
        this.memberId = memberId;
        this.auctionScheduleId = auctionScheduleId;
        this.reservationInfo = reservationInfo;
    }

    public static AuctionReservation of(boolean isDeleted, Long memberId, Integer auctionScheduleId, ReservationInfo reservationInfo) {
        return new AuctionReservation(isDeleted, memberId, auctionScheduleId, reservationInfo);
    }

    public static AuctionReservation create(Long memberId, Integer auctionScheduleId, ReservationInfo reservationInfo) {
        return of(false, memberId, auctionScheduleId, reservationInfo);
    }
}
