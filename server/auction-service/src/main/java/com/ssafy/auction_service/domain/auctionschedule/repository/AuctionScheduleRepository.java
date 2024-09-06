package com.ssafy.auction_service.domain.auctionschedule.repository;

import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionScheduleRepository extends JpaRepository<AuctionSchedule, Integer> {

    @Query(value = "select s.id from AuctionSchedule s where s.auctionInfo=:auctionInfo")
    Optional<Integer> findIdByAuction(AuctionInfo auctionInfo);
}
