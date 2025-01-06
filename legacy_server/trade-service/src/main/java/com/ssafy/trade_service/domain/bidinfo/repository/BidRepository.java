package com.ssafy.trade_service.domain.bidinfo.repository;

import com.ssafy.trade_service.domain.bidinfo.Bid;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends MongoRepository<Bid, Long> {

    String NO_SUCH_BID = "낙찰받은 품종이 없습니다.";
}
