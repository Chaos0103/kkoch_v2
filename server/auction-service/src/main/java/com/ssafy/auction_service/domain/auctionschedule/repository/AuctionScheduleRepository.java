package com.ssafy.auction_service.domain.auctionschedule.repository;

import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionScheduleRepository extends JpaRepository<AuctionSchedule, Integer> {

    String NO_SUCH_AUCTION_SCHEDULE = "등록되지 않은 경매 일정입니다.";

    @Query(value = "select s.id from AuctionSchedule s where s.auctionInfo=:auctionInfo")
    Optional<Integer> findIdByAuction(AuctionInfo auctionInfo);
}
