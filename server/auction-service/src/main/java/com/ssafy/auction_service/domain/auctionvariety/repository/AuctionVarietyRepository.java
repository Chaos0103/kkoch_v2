package com.ssafy.auction_service.domain.auctionvariety.repository;

import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionvariety.AuctionVariety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionVarietyRepository extends JpaRepository<AuctionVariety, Long> {

    int countByAuctionSchedule(AuctionSchedule auctionSchedule);
}
