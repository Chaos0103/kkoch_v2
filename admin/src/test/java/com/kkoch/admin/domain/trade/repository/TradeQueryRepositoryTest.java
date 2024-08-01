package com.kkoch.admin.domain.trade.repository;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.response.TradeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class TradeQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private TradeQueryRepository tradeQueryRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @DisplayName("회원 고유키를 입력 받아 일치하는 거래 내역 ID 목록을 조회한다.")
    @Test
    void findAllIdByCond() {
        //given
        String memberKey = UUID.randomUUID().toString();

        Trade trade1 = createTrade(memberKey, false);
        Trade trade2 = createTrade(memberKey, true);
        Trade trade3 = createTrade(memberKey, false);

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        List<Long> tradeIds = tradeQueryRepository.findAllIdByCond(memberKey, pageRequest);

        //then
        assertThat(tradeIds).hasSize(2)
            .containsExactly(trade3.getId(), trade1.getId());
    }

    @DisplayName("거래 내역 ID 목록을 입력 받아 일치하는 거래 내역 목록을 조회한다.")
    @Test
    void findAllByIdIn() {
        //given
        String memberKey = UUID.randomUUID().toString();

        Trade trade1 = createTrade(memberKey, false);
        Trade trade2 = createTrade(memberKey, false);

        List<Long> tradeIds = List.of(trade2.getId(), trade1.getId());

        //when
        List<Trade> trades = tradeQueryRepository.findAllByIdIn(tradeIds);

        //then
        assertThat(trades).hasSize(2)
            .extracting("id", "totalPrice")
            .containsExactly(
                tuple(trade2.getId(), 100_000),
                tuple(trade1.getId(), 100_000)
            );
    }

    @DisplayName("회원 고유키를 입력 받아 일치하는 거래 내역 총 갯수를 조회한다.")
    @Test
    void countByCond() {
        //given
        String memberKey = UUID.randomUUID().toString();

        Trade trade1 = createTrade(memberKey, false);
        Trade trade2 = createTrade(memberKey, true);
        Trade trade3 = createTrade(memberKey, false);

        //when
        int total = tradeQueryRepository.countByCond(memberKey);

        //then
        assertThat(total).isEqualTo(2);
    }

    private Trade createTrade(String memberKey, boolean isDeleted) {
        Trade trade = Trade.builder()
            .isDeleted(isDeleted)
            .memberKey(memberKey)
            .totalPrice(100_000)
            .tradeDateTime(null)
            .isPickUp(false)
            .auctionScheduleId(1)
            .build();
        return tradeRepository.save(trade);
    }
}