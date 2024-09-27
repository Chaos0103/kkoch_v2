package com.ssafy.live_service.api.service.auctionevent;

import com.ssafy.live_service.IntegrationTestSupport;
import com.ssafy.live_service.api.service.auctionevent.request.BidServiceRequest;
import com.ssafy.live_service.api.service.auctionevent.vo.BidInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuctionEventServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionEventService auctionEventService;

    @Autowired
    private RedisTemplate<String, BidInfo> redisTemplate;

    @AfterEach
    void tearDown() {
        Long size = redisTemplate.opsForZSet().size(String.valueOf(1L));
        redisTemplate.opsForZSet()
            .removeRange(String.valueOf(1L), 0, size);
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
}