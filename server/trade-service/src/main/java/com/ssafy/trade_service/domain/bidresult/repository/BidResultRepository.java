package com.ssafy.trade_service.domain.bidresult.repository;

import com.ssafy.trade_service.domain.bidresult.BidResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidResultRepository extends JpaRepository<BidResult, Long> {
}
