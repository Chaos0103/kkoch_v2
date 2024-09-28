package com.ssafy.live_service.api.service.auction;

import com.ssafy.live_service.api.ApiResponse;
import com.ssafy.live_service.api.client.MemberServiceClient;
import com.ssafy.live_service.api.client.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionRoomService {

    private final RedisTemplate<String, Integer> redisTemplate;
    private final MemberServiceClient memberServiceClient;

    public int generateParticipationNumber(int auctionScheduleId) {
        //회원 고유키 조회
        String memberKey = getMemberKey();

        Integer participationNumber = (Integer) redisTemplate.opsForHash().get(String.valueOf(auctionScheduleId), memberKey);
        if (participationNumber != null) {
            return participationNumber;
        }

        //회원 번호 발급
        int size = getParticipationSizeByKey(auctionScheduleId);
        int generatedParticipationNumber = size + 1;
        redisTemplate.opsForHash().put(String.valueOf(auctionScheduleId), memberKey, generatedParticipationNumber);

        return generatedParticipationNumber;
    }

    private int getParticipationSizeByKey(int auctionScheduleId) {
        return redisTemplate.opsForHash().size(String.valueOf(auctionScheduleId))
            .intValue();
    }

    private String getMemberKey() {
        ApiResponse<MemberResponse> response = memberServiceClient.searchMemberSeq();
        return response.getData().getMemberKey();
    }
}
