package com.kkoch.admin.api.service.bidresult;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.bidresult.request.BidResultCreateServiceRequest;
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
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import com.kkoch.admin.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class BidResultServiceTest extends IntegrationTestSupport {

    @Autowired
    private BidResultService bidResultService;

    @Autowired
    private BidResultRepository bidResultRepository;

    @Autowired
    private AuctionVarietyRepository auctionVarietyRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @Autowired
    private VarietyRepository varietyRepository;

    @DisplayName("경매 결과를 입력 받아 신규 등록한다. 낙찰자가 해당 경매에 거래 내역이 없는 경우 거래 내역을 생성 후 등록한다.")
    @Test
    void createBidResultTradeIsEmpty() {
        //given
        String memberKey = UUID.randomUUID().toString();

        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule();
        AuctionVariety auctionVariety = createAuctionVariety(variety, auctionSchedule);

        BidResultCreateServiceRequest request = BidResultCreateServiceRequest.builder()
            .auctionVarietyId(auctionVariety.getId())
            .bidPrice(3500)
            .bidDateTime(LocalDateTime.of(2024, 7, 12, 6, 11))
            .build();

        //when
        long bidResultId = bidResultService.createBidResult(memberKey, request);

        //then
        List<Trade> trades = tradeRepository.findAll();
        assertThat(trades).hasSize(1)
            .extracting("memberKey", "totalPrice", "tradeDateTime", "isPickUp", "auctionScheduleId")
            .containsExactly(
                tuple(memberKey, 3500, null, false, auctionSchedule.getId())
            );

        Optional<BidResult> bidResult = bidResultRepository.findById(bidResultId);
        assertThat(bidResult).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("bidPrice", 3500)
            .hasFieldOrPropertyWithValue("bidDateTime", LocalDateTime.of(2024, 7, 12, 6, 11))
            .hasFieldOrPropertyWithValue("trade.id", trades.get(0).getId());
    }

    @DisplayName("경매 결과를 입력 받아 신규 등록한다. 낙찰자가 해당 경매에 거래 내역이 있는 경우 기존 거래 내역을 사용한다.")
    @Test
    void createBidResultTradeIsPersist() {
        //given
        String memberKey = UUID.randomUUID().toString();

        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule();
        Trade trade = createTrade(memberKey, 3000, auctionSchedule.getId());

        AuctionVariety auctionVariety = createAuctionVariety(variety, auctionSchedule);

        BidResultCreateServiceRequest request = BidResultCreateServiceRequest.builder()
            .auctionVarietyId(auctionVariety.getId())
            .bidPrice(3500)
            .bidDateTime(LocalDateTime.of(2024, 7, 12, 6, 11))
            .build();

        //when
        long bidResultId = bidResultService.createBidResult(memberKey, request);

        //then
        List<Trade> trades = tradeRepository.findAll();
        assertThat(trades).hasSize(1)
            .extracting("memberKey", "totalPrice", "tradeDateTime", "isPickUp", "auctionScheduleId")
            .containsExactly(
                tuple(memberKey, 6500, null, false, auctionSchedule.getId())
            );

        Optional<BidResult> bidResult = bidResultRepository.findById(bidResultId);
        assertThat(bidResult).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("bidPrice", 3500)
            .hasFieldOrPropertyWithValue("bidDateTime", LocalDateTime.of(2024, 7, 12, 6, 11))
            .hasFieldOrPropertyWithValue("trade.id", trades.get(0).getId());
    }

    private Variety createVariety() {
        Variety variety = Variety.builder()
            .isDeleted(false)
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
            .roomStatus(AuctionRoomStatus.READY)
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

    private Trade createTrade(String memberKey, int totalPrice, int auctionScheduleId) {
        Trade trade = Trade.builder()
            .isDeleted(false)
            .memberKey(memberKey)
            .totalPrice(totalPrice)
            .tradeDateTime(null)
            .isPickUp(false)
            .auctionScheduleId(auctionScheduleId)
            .build();
        return tradeRepository.save(trade);
    }
}