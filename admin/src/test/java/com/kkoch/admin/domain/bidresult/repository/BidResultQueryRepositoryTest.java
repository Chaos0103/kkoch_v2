package com.kkoch.admin.domain.bidresult.repository;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import com.kkoch.admin.domain.auctionvariety.AuctionVarietyInfo;
import com.kkoch.admin.domain.auctionvariety.ShippingInfo;
import com.kkoch.admin.domain.auctionvariety.repository.AuctionVarietyRepository;
import com.kkoch.admin.domain.bidresult.repository.dto.BidResultSearchCond;
import com.kkoch.admin.domain.bidresult.BidResult;
import com.kkoch.admin.domain.bidresult.repository.response.BidResultResponse;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.TradeRepository;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import com.kkoch.admin.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class BidResultQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private BidResultQueryRepository bidResultQueryRepository;

    @Autowired
    private AuctionVarietyRepository auctionVarietyRepository;

    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private BidResultRepository bidResultRepository;

    @DisplayName("검색 조건을 입력 받아 낙찰 결과 ID 목록을 조회한다.")
    @Test
    void findAllIdByCond() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule();
        AuctionVariety auctionVariety = createAuctionVariety(variety, auctionSchedule);
        Trade trade = createTrade();
        BidResult bidResult1 = createBidResult(auctionVariety, trade, 3500, LocalDateTime.of(2024, 7, 12, 4, 59, 59));
        BidResult bidResult2 = createBidResult(auctionVariety, trade, 3600, LocalDateTime.of(2024, 7, 12, 5, 0, 0));
        BidResult bidResult3 = createBidResult(auctionVariety, trade, 3700, LocalDateTime.of(2024, 7, 12, 6, 0, 0));
        BidResult bidResult4 = createBidResult(auctionVariety, trade, 3800, LocalDateTime.of(2024, 7, 12, 6, 0, 1));

        PageRequest pageRequest = PageRequest.of(0, 10);
        BidResultSearchCond cond = BidResultSearchCond.builder()
            .startDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .endDateTime(LocalDateTime.of(2024, 7, 12, 6, 0))
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .build();

        //when
        List<Long> bidResultIds = bidResultQueryRepository.findAllIdByCond(cond, pageRequest);

        //then
        assertThat(bidResultIds).hasSize(2)
            .containsExactly(bidResult3.getId(), bidResult2.getId());
    }

    @DisplayName("경매 결과 ID 목록을 입력 받아 낙찰 결과 목록을 조회한다.")
    @Test
    void findAllByIdIn() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule();
        AuctionVariety auctionVariety = createAuctionVariety(variety, auctionSchedule);
        Trade trade = createTrade();
        BidResult bidResult1 = createBidResult(auctionVariety, trade, 3600, LocalDateTime.of(2024, 7, 12, 5, 0, 0));
        BidResult bidResult2 = createBidResult(auctionVariety, trade, 3700, LocalDateTime.of(2024, 7, 12, 6, 0, 0));

        List<Long> bidResultIds = List.of(bidResult2.getId(), bidResult1.getId());

        //when
        List<BidResultResponse> content = bidResultQueryRepository.findAllByIdIn(bidResultIds);

        //then
        assertThat(content).hasSize(2)
            .extracting("bidPrice", "bidDateTime")
            .containsExactly(
                tuple(3700, LocalDateTime.of(2024, 7, 12, 6, 0, 0)),
                tuple(3600, LocalDateTime.of(2024, 7, 12, 5, 0, 0))
            );
    }

    @DisplayName("검색 조건을 입력 받아 총 낙찰 결과 갯수를 조회한다.")
    @Test
    void countByCond() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule();
        AuctionVariety auctionVariety = createAuctionVariety(variety, auctionSchedule);
        Trade trade = createTrade();
        BidResult bidResult1 = createBidResult(auctionVariety, trade, 3500, LocalDateTime.of(2024, 7, 12, 4, 59, 59));
        BidResult bidResult2 = createBidResult(auctionVariety, trade, 3600, LocalDateTime.of(2024, 7, 12, 5, 0, 0));
        BidResult bidResult3 = createBidResult(auctionVariety, trade, 3700, LocalDateTime.of(2024, 7, 12, 6, 0, 0));
        BidResult bidResult4 = createBidResult(auctionVariety, trade, 3800, LocalDateTime.of(2024, 7, 12, 6, 0, 1));

        BidResultSearchCond cond = BidResultSearchCond.builder()
            .startDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .endDateTime(LocalDateTime.of(2024, 7, 12, 6, 0))
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .build();

        //when
        int total = bidResultQueryRepository.countByCond(cond);

        //then
        assertThat(total).isEqualTo(2);
    }

    private Variety createVariety() {
        Variety variety = Variety.builder()
            .isDeleted(false)
            .createdBy(1)
            .lastModifiedBy(1)
            .code("10000001")
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();
        return varietyRepository.save(variety);
    }

    private AuctionSchedule createAuctionSchedule() {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1)
            .lastModifiedBy(1)
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .roomStatus(AuctionRoomStatus.OPEN)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }

    private AuctionVariety createAuctionVariety(Variety variety, AuctionSchedule auctionSchedule) {
        AuctionVariety auctionVariety = AuctionVariety.builder()
            .isDeleted(false)
            .createdBy(1)
            .lastModifiedBy(1)
            .auctionNumber("00001")
            .auctionVarietyInfo(AuctionVarietyInfo.builder()
                .grade(Grade.SUPER)
                .plantCount(10)
                .startPrice(4500)
                .build())
            .shippingInfo(ShippingInfo.builder()
                .region("광주")
                .shipper("김판매")
                .build())
            .variety(variety)
            .auctionSchedule(auctionSchedule)
            .build();
        return auctionVarietyRepository.save(auctionVariety);
    }

    private Trade createTrade() {
        Trade trade = Trade.builder()
            .isDeleted(false)
            .memberKey(UUID.randomUUID().toString())
            .build();
        return tradeRepository.save(trade);
    }

    private BidResult createBidResult(AuctionVariety auctionVariety, Trade trade, int bidPrice, LocalDateTime bidDateTime) {
        BidResult bidResult = BidResult.builder()
            .isDeleted(false)
            .bidPrice(bidPrice)
            .bidDateTime(bidDateTime)
            .auctionVariety(auctionVariety)
            .trade(trade)
            .build();
        return bidResultRepository.save(bidResult);
    }

}