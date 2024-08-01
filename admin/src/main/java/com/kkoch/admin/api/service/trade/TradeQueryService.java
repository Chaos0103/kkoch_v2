package com.kkoch.admin.api.service.trade;

import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.api.service.trade.response.TradeDetailResponse;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleQueryRepository;
import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.kkoch.admin.domain.auctionschedule.repository.response.AuctionDateTimeVo;
import com.kkoch.admin.domain.bidresult.BidResult;
import com.kkoch.admin.domain.bidresult.repository.BidResultQueryRepository;
import com.kkoch.admin.domain.bidresult.repository.vo.BidResultCountVo;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.TradeQueryRepository;
import com.kkoch.admin.domain.trade.repository.TradeRepository;
import com.kkoch.admin.domain.trade.repository.response.TradeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TradeQueryService {

    private final TradeRepository tradeRepository;
    private final AuctionScheduleRepository auctionScheduleRepository;
    private final TradeQueryRepository tradeQueryRepository;
    private final AuctionScheduleQueryRepository auctionScheduleQueryRepository;
    private final BidResultQueryRepository bidResultQueryRepository;

    public PageResponse<TradeResponse> searchTrades(String memberKey, Pageable pageable) {
        int total = tradeQueryRepository.countByCond(memberKey);

        List<Long> tradeIds = tradeQueryRepository.findAllIdByCond(memberKey, pageable);
        if (CollectionUtils.isEmpty(tradeIds)) {
            return PageResponse.empty(pageable, total);
        }

        List<Trade> trades = tradeQueryRepository.findAllByIdIn(tradeIds);
        List<Integer> auctionScheduleIds = createAuctionScheduleIdListBy(trades);

        List<AuctionDateTimeVo> auctionDateTimes = auctionScheduleQueryRepository.findAllAuctionDataTimeByIdIn(auctionScheduleIds);
        Map<Integer, LocalDateTime> auctionDateTimeMap = createAuctionDateTimeMapBy(auctionDateTimes);

        List<BidResultCountVo> bidResultCounts = bidResultQueryRepository.findAllByTradeIdIn(tradeIds);
        Map<Long, Long> bidResultCountMap = createBidResultCountMapBy(bidResultCounts);

        List<TradeResponse> content = createTradeResponseListBy(trades, bidResultCountMap, auctionDateTimeMap);

        return PageResponse.create(content, pageable, total);
    }

    public TradeDetailResponse searchTrade(long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 거래입니다."));

        AuctionSchedule auctionSchedule = auctionScheduleRepository.findById(trade.getAuctionScheduleId())
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 경매 일정입니다."));

        List<BidResult> bidResults = bidResultQueryRepository.findAllByTradeId(tradeId);

        return TradeDetailResponse.of(trade, auctionSchedule, bidResults);
    }

    private List<Integer> createAuctionScheduleIdListBy(List<Trade> trades) {
        return trades.stream()
            .map(Trade::getAuctionScheduleId)
            .collect(Collectors.toList());
    }

    private Map<Integer, LocalDateTime> createAuctionDateTimeMapBy(List<AuctionDateTimeVo> auctionDateTimes) {
        return auctionDateTimes.stream()
            .collect(Collectors.toMap(AuctionDateTimeVo::getId, AuctionDateTimeVo::getAuctionDateTime, (a, b) -> b));
    }

    private Map<Long, Long> createBidResultCountMapBy(List<BidResultCountVo> bidResultCounts) {
        return bidResultCounts.stream()
            .collect(Collectors.toMap(BidResultCountVo::getTradeId, BidResultCountVo::getCount, (a, b) -> b));
    }

    private List<TradeResponse> createTradeResponseListBy(List<Trade> trades, Map<Long, Long> bidResultCountMap, Map<Integer, LocalDateTime> auctionDateTimeMap) {
        return trades.stream()
            .map(trade -> TradeResponse.of(trade, bidResultCountMap.get(trade.getId()), auctionDateTimeMap.get(trade.getAuctionScheduleId())))
            .collect(Collectors.toList());
    }
}
