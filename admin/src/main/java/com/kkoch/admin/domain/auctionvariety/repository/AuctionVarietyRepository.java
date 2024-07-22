package com.kkoch.admin.domain.auctionvariety.repository;

import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionVarietyRepository extends JpaRepository<AuctionVariety, Long> {

    int countByAuctionSchedule(AuctionSchedule auctionSchedule);
}
