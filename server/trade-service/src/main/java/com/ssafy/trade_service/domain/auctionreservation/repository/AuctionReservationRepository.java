package com.ssafy.trade_service.domain.auctionreservation.repository;

import com.ssafy.trade_service.domain.auctionreservation.AuctionReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionReservationRepository extends JpaRepository<AuctionReservation, Long> {

    String NO_SUCH_AUCTION_RESERVATION = "등록되지 않은 경매 예약입니다.";

    @Query("select ar.reservationInfo.plantCount from AuctionReservation ar where ar.isDeleted = false and ar.auctionScheduleId = :auctionScheduleId and ar.memberId = :memberId")
    List<Integer> findAllPlantCountByAuctionScheduleId(Integer auctionScheduleId, Long memberId);
}
