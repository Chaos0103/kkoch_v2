package com.ssafy.live_service.api.service.auction;

import com.ssafy.live_service.api.client.MemberServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionRoomService {

    private final StringRedisTemplate redisTemplate;
    private final MemberServiceClient memberServiceClient;

    public int generateParticipationNumber(int auctionScheduleId) {
        //회원 고유키 조회
        //회원 번호 발급
        return 0;
    }
}
