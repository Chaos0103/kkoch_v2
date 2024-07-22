package com.kkoch.admin.domain.trade.repository;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.domain.trade.Trade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TradeRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private TradeRepository tradeRepository;

    @DisplayName("회원 고유키와 경매 일정 ID를 입력 받아 거래를 조회한다.")
    @Test
    void findByMemberKeyAndAuctionScheduleId() {
        //given
        String memberKey = UUID.randomUUID().toString();

        Trade trade1 = createTrade(memberKey, LocalDateTime.of(2024, 7, 7, 6, 30), 1);
        Trade trade2 = createTrade(memberKey, null, 2);

        //when
        Optional<Trade> trade = tradeRepository.findByMemberKeyAndAuctionScheduleId(memberKey, 2);

        //then
        assertThat(trade).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("memberKey", memberKey)
            .hasFieldOrPropertyWithValue("tradeDateTime", null)
            .hasFieldOrPropertyWithValue("auctionScheduleId", 2);
    }

    private Trade createTrade(String memberKey, LocalDateTime tradeDateTime, int auctionScheduleId) {
        Trade trade = Trade.builder()
            .isDeleted(false)
            .memberKey(memberKey)
            .totalPrice(100_000)
            .tradeDateTime(tradeDateTime)
            .isPickUp(false)
            .auctionScheduleId(auctionScheduleId)
            .build();
        return tradeRepository.save(trade);
    }

}