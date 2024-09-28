package com.ssafy.live_service.api.service.auctionevent;

import com.ssafy.live_service.IntegrationTestSupport;
import com.ssafy.live_service.api.service.auctionevent.request.BidServiceRequest;
import com.ssafy.live_service.api.service.auctionevent.response.AuctionEventResponse;
import com.ssafy.live_service.api.service.auctionevent.vo.BidInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuctionEventServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionEventService auctionEventService;

    @Autowired
    private RedisTemplate<String, BidInfo> redisTemplate;

    @AfterEach
    void tearDown() {
        redisTemplate.delete(String.valueOf(1L));
    }

    @DisplayName("동일한 회원이 여러번 요청을 보내면 대기열에서 후순위가 된다.")
    @Test
    void addQueueDuplicateRequest() {
        //given
        String memberKey1 = UUID.randomUUID().toString();
        LocalDateTime current = LocalDateTime.of(2024, 8, 15, 5, 10, 0);
        BidServiceRequest request = BidServiceRequest.builder()
            .auctionVarietyId(1L)
            .varietyCode("10031204")
            .plantGrade("SUPER")
            .plantCount(10)
            .bidPrice(3000)
            .build();
        String memberKey2 = UUID.randomUUID().toString();

        auctionEventService.addQueue(memberKey1, request, current, 1L);
        auctionEventService.addQueue(memberKey2, request, current, 2L);

        //when
        boolean result = auctionEventService.addQueue(memberKey1, request, current, 3L);

        //then
        assertThat(result).isTrue();

        String key = String.valueOf(1L);
        Long size = redisTemplate.opsForZSet().size(key);
        assertThat(size).isNotNull()
            .isEqualTo(2);

        Set<BidInfo> bidInfos = redisTemplate.opsForZSet().range(key, 0, size);
        assertThat(bidInfos).hasSize(2)
            .extracting("memberKey")
            .containsExactly(memberKey2, memberKey1);
    }

    @DisplayName("낙찰을 시도한 회원을 대기열에 삽입한다.")
    @Test
    void addQueue() {
        //given
        String memberKey = UUID.randomUUID().toString();
        LocalDateTime current = LocalDateTime.of(2024, 8, 15, 5, 10, 0);
        BidServiceRequest request = BidServiceRequest.builder()
            .auctionVarietyId(1L)
            .varietyCode("10031204")
            .plantGrade("SUPER")
            .plantCount(10)
            .bidPrice(3000)
            .build();

        //when
        boolean result = auctionEventService.addQueue(memberKey, request, current, 1L);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("가장 먼저 요청한 회원이 품종을 낙찰받는다.")
    @Test
    void publish() {
        //given
        String memberKey1 = UUID.randomUUID().toString();
        String memberKey2 = UUID.randomUUID().toString();
        String memberKey3 = UUID.randomUUID().toString();

        createEvent(memberKey1, 3000, 2L);
        createEvent(memberKey2, 3100, 1L);
        createEvent(memberKey3, 3200, 3L);

        //when
        AuctionEventResponse response = auctionEventService.publish(1L);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("bidPrice", 3100);
        Long size = redisTemplate.opsForZSet().size(String.valueOf(1L));
        assertThat(size).isNotNull()
            .isEqualTo(0);
    }

    private void createEvent(String memberKey, int bidPrice, long millis) {
        BidInfo bidInfo = BidInfo.builder()
            .memberKey(memberKey)
            .auctionVarietyId(1L)
            .varietyCode("10031204")
            .plantGrade("SUPER")
            .plantCount(10)
            .bidPrice(bidPrice)
            .bidDateTime(LocalDateTime.of(2024, 8, 15, 5, 10))
            .build();
        redisTemplate.opsForZSet().add(String.valueOf(1L), bidInfo, millis);
    }
}