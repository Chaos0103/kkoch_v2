package com.kkoch.admin.api.service.trade;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.api.service.trade.response.TradeDetailResponse;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import com.kkoch.admin.domain.auctionvariety.AuctionVarietyInfo;
import com.kkoch.admin.domain.auctionvariety.ShippingInfo;
import com.kkoch.admin.domain.auctionvariety.repository.AuctionVarietyRepository;
import com.kkoch.admin.domain.bidresult.BidResult;
import com.kkoch.admin.domain.bidresult.repository.BidResultRepository;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.TradeRepository;
import com.kkoch.admin.domain.trade.repository.response.TradeResponse;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import com.kkoch.admin.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class TradeQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private TradeQueryService tradeQueryService;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @Autowired
    private BidResultRepository bidResultRepository;

    @Autowired
    private AuctionVarietyRepository auctionVarietyRepository;

    @Autowired
    private VarietyRepository varietyRepository;

    @DisplayName("회원 고유키를 입력 받아 거래 내역 목록을 조회한다.")
    @Test
    void searchTrades() {
        //given
        String memberKey = UUID.randomUUID().toString();

        Variety variety = createVariety();
        AuctionSchedule auctionSchedule1 = createAuctionSchedule(LocalDateTime.of(2024, 7, 12, 5, 0));
        AuctionVariety auctionVariety1 = createAuctionVariety(variety, auctionSchedule1);
        AuctionVariety auctionVariety2 = createAuctionVariety(variety, auctionSchedule1);

        Trade trade1 = createTrade(memberKey, auctionSchedule1.getId());
        BidResult bidResult1 = createBidResult(auctionVariety1, trade1, 3500, LocalDateTime.of(2024, 7, 12, 6, 30));
        BidResult bidResult2 = createBidResult(auctionVariety2, trade1, 3500, LocalDateTime.of(2024, 7, 12, 6, 30));

        AuctionSchedule auctionSchedule2 = createAuctionSchedule(LocalDateTime.of(2024, 7, 19, 5, 0));
        AuctionVariety auctionVariety3 = createAuctionVariety(variety, auctionSchedule2);

        Trade trade2 = createTrade(memberKey, auctionSchedule2.getId());
        BidResult bidResult3 = createBidResult(auctionVariety3, trade2, 3500, LocalDateTime.of(2024, 7, 12, 6, 30));

        PageRequest pageRequest = PageRequest.of(0, 15);

        //when
        PageResponse<TradeResponse> response = tradeQueryService.searchTrades(memberKey, pageRequest);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 15)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);

        assertThat(response.getContent()).hasSize(2);
    }

    @DisplayName("거래 내역 ID를 입력 받아 거래 내역을 상세 조회한다.")
    @Test
    void searchTrade() {
        //given
        String memberKey = UUID.randomUUID().toString();

        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(LocalDateTime.of(2024, 7, 12, 5, 0));
        AuctionVariety auctionVariety1 = createAuctionVariety(variety, auctionSchedule);
        AuctionVariety auctionVariety2 = createAuctionVariety(variety, auctionSchedule);
        Trade trade = createTrade(memberKey, auctionSchedule.getId());

        BidResult bidResult1 = createBidResult(auctionVariety1, trade, 3500, LocalDateTime.of(2024, 7, 12, 6, 30));
        BidResult bidResult2 = createBidResult(auctionVariety2, trade, 4000, LocalDateTime.of(2024, 7, 12, 6, 32));

        //when
        TradeDetailResponse response = tradeQueryService.searchTrade(trade.getId());

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("tradeId", trade.getId())
            .hasFieldOrPropertyWithValue("totalPrice", trade.getTotalPrice())
            .hasFieldOrPropertyWithValue("tradeDateTime", trade.getTradeDateTime())
            .hasFieldOrPropertyWithValue("isPickUp", trade.isPickUp())
            .hasFieldOrPropertyWithValue("auctionSchedule.plantCategory", PlantCategory.CUT_FLOWERS)
            .hasFieldOrPropertyWithValue("auctionSchedule.auctionDateTime", LocalDateTime.of(2024, 7, 12, 5, 0));
        assertThat(response.getResults()).hasSize(2)
            .extracting("varietyCode", "itemName", "varietyName", "grade", "plantCount", "bidPrice", "bidDateTime")
            .containsExactly(
                tuple("10000001", "장미", "하트앤소울", Grade.SUPER, 10, 4000, LocalDateTime.of(2024, 7, 12, 6, 32)),
                tuple("10000001", "장미", "하트앤소울", Grade.SUPER, 10, 3500, LocalDateTime.of(2024, 7, 12, 6, 30))
            );
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

    private AuctionSchedule createAuctionSchedule(LocalDateTime auctionDateTime) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1)
            .lastModifiedBy(1)
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .roomStatus(AuctionRoomStatus.CLOSE)
            .auctionDateTime(auctionDateTime)
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }

    private AuctionVariety createAuctionVariety(Variety variety, AuctionSchedule auctionSchedule) {
        AuctionVariety auctionVariety = AuctionVariety.builder()
            .isDeleted(false)
            .createdBy(1)
            .lastModifiedBy(1)
            .auctionNumber("00001")
            .auctionVarietyInfo(
                AuctionVarietyInfo.builder()
                    .grade(Grade.SUPER)
                    .plantCount(10)
                    .startPrice(4500)
                    .build()
            )
            .shippingInfo(
                ShippingInfo.builder()
                    .region("광주")
                    .shipper("김판매")
                    .build()
            )
            .variety(variety)
            .auctionSchedule(auctionSchedule)
            .build();
        return auctionVarietyRepository.save(auctionVariety);
    }

    private Trade createTrade(String memberKey, int auctionScheduleId) {
        Trade trade = Trade.builder()
            .isDeleted(false)
            .memberKey(memberKey)
            .totalPrice(100_000)
            .tradeDateTime(null)
            .isPickUp(false)
            .auctionScheduleId(auctionScheduleId)
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