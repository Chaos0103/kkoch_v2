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

    private static final String AUCTION_ROOM_PARTICIPATE_KEY_FORMAT = "AUCTION-PARTICIPATION-%d";

    private final RedisTemplate<String, Integer> redisTemplate;
    private final MemberServiceClient memberServiceClient;

    public int generateParticipationNumber(int auctionScheduleId) {
        String key = generateKey(auctionScheduleId);

        String memberKey = getMemberKey();

        Integer participationNumber = (Integer) redisTemplate.opsForHash().get(key, memberKey);
        if (participationNumber != null) {
            return participationNumber;
        }

        int size = getParticipationSizeByKey(key);
        int generatedParticipationNumber = size + 1;
        redisTemplate.opsForHash().put(key, memberKey, generatedParticipationNumber);

        return generatedParticipationNumber;
    }

    private String generateKey(int auctionScheduleId) {
        return String.format(AUCTION_ROOM_PARTICIPATE_KEY_FORMAT, auctionScheduleId);
    }

    private int getParticipationSizeByKey(String key) {
        return redisTemplate.opsForHash().size(key)
            .intValue();
    }

    private String getMemberKey() {
        ApiResponse<MemberResponse> response = memberServiceClient.searchMemberSeq();
        return response.getData().getMemberKey();
    }
}
