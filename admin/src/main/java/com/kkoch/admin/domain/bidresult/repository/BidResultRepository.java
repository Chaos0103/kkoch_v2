package com.kkoch.admin.domain.bidresult.repository;

import com.kkoch.admin.domain.bidresult.BidResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidResultRepository extends JpaRepository<BidResult, Long> {
}
