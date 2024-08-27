package com.ssafy.auction_service.domain.auctionschedule.repository;

import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionScheduleRepository extends JpaRepository<AuctionSchedule, Integer> {
}
