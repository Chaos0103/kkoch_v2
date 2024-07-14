package com.kkoch.admin.domain.auctionschedule.repository;

import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionScheduleRepository extends JpaRepository<AuctionSchedule, Integer> {
}
