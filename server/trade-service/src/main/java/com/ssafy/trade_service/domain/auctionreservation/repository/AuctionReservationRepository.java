package com.ssafy.trade_service.domain.auctionreservation.repository;

import com.ssafy.trade_service.domain.auctionreservation.AuctionReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionReservationRepository extends JpaRepository<AuctionReservation, Long> {
}
