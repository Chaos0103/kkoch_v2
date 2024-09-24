package com.ssafy.trade_service.domain.bidresult.repository;

import com.ssafy.trade_service.domain.bidresult.BidResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BidResultRepository extends JpaRepository<BidResult, Long> {

    @Query("select br from BidResult br where br.bidDateTime >= :from and br.bidDateTime < :to")
    List<BidResult> findAllByBidDateTime(LocalDateTime from, LocalDateTime to);
}
