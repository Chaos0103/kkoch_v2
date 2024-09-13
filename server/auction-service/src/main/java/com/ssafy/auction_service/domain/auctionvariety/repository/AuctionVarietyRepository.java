package com.ssafy.auction_service.domain.auctionvariety.repository;

import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionvariety.AuctionVariety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionVarietyRepository extends JpaRepository<AuctionVariety, Long> {

    String NO_SUCH_AUCTION_VARIETY = "등록되지 않은 경매 품종입니다.";

    int countByAuctionSchedule(AuctionSchedule auctionSchedule);
}
