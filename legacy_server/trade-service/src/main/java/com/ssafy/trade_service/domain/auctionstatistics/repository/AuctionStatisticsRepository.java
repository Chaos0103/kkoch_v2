package com.ssafy.trade_service.domain.auctionstatistics.repository;

import com.ssafy.trade_service.domain.auctionstatistics.AuctionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionStatisticsRepository extends JpaRepository<AuctionStatistics, Long> {
}
