package com.ssafy.auction_service.domain.auctionschedule.repository;

import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AuctionScheduleRepository extends JpaRepository<AuctionSchedule, Integer> {

    @Query(value = "select s.id from AuctionSchedule s where s.auctionInfo.plantCategory=:plantCategory and s.auctionInfo.jointMarket=:jointMarket and s.auctionStartDateTime=:auctionStartDateTime")
    Optional<Integer> findIdByAuction(PlantCategory plantCategory, JointMarket jointMarket, LocalDateTime auctionStartDateTime);
}
