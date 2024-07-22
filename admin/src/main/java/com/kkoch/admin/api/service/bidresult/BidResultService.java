package com.kkoch.admin.api.service.bidresult;

import com.kkoch.admin.api.service.bidresult.request.BidResultCreateServiceRequest;
import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import com.kkoch.admin.domain.auctionvariety.repository.AuctionVarietyRepository;
import com.kkoch.admin.domain.bidresult.BidResult;
import com.kkoch.admin.domain.bidresult.repository.BidResultRepository;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class BidResultService {

    private final BidResultRepository bidResultRepository;
    private final AuctionVarietyRepository auctionVarietyRepository;
    private final TradeRepository tradeRepository;

    public long createBidResult(String memberKey, BidResultCreateServiceRequest request) {
        AuctionVariety auctionVariety = auctionVarietyRepository.findById(request.getAuctionVarietyId())
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 경매품종 입니다."));

        Trade trade = tradeRepository.findByMemberKeyAndAuctionScheduleId(memberKey, auctionVariety.getAuctionScheduleId())
            .orElse(Trade.create(memberKey, auctionVariety.getAuctionScheduleId()));

        BidResult bidResult = request.toEntity(auctionVariety, trade);
        BidResult savedBidResult = bidResultRepository.save(bidResult);

        return savedBidResult.getId();
    }
}
