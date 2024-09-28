package com.ssafy.live_service.api.service.auction;

import com.ssafy.live_service.IntegrationTestSupport;
import com.ssafy.live_service.api.ApiResponse;
import com.ssafy.live_service.api.client.MemberServiceClient;
import com.ssafy.live_service.api.client.response.MemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuctionRoomServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionRoomService auctionRoomService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @MockBean
    private MemberServiceClient memberServiceClient;


    @DisplayName("한 경매에 동일한 회원이 여러번 참가해도 최초에 발급받은 번호를 사용한다.")
    @Test
    void generateParticipationNumberSeveralTimes() {
        //given
        int participationNumber = 1;
        String memberKey = UUID.randomUUID().toString();

        auctionParticipation(1, memberKey, participationNumber);
        mockingMember(memberKey, 1L);

        //when
        int generatedParticipationNumber = auctionRoomService.generateParticipationNumber(1);

        //then
        assertThat(generatedParticipationNumber).isOne();
    }

    @DisplayName("경매 참가자의 번호 생성시 1번부터 시작한다.")
    @Test
    void generateParticipationNumberIsFirst() {
        //given
        String memberKey = UUID.randomUUID().toString();
        mockingMember(memberKey, 1L);

        //when
        int generatedParticipationNumber = auctionRoomService.generateParticipationNumber(1);

        //then
        assertThat(generatedParticipationNumber).isOne();
    }

    @DisplayName("경매 참가자의 번호 생성시 마지막 참가자 번호에서 1증가한 값을 가진다.")
    @Test
    void generateParticipationNumber() {
        //given
        int anotherParticipationNumber = 2;
        String anotherMemberKey = UUID.randomUUID().toString();
        auctionParticipation(1, anotherMemberKey, anotherParticipationNumber);

        String memberKey = UUID.randomUUID().toString();
        mockingMember(memberKey, 1L);

        //when
        int generatedParticipationNumber = auctionRoomService.generateParticipationNumber(1);

        //then
        assertThat(generatedParticipationNumber).isEqualTo(2);
    }

    private void mockingMember(String memberKey, Long memberId) {
        MemberResponse member = MemberResponse.builder()
            .memberId(memberId)
            .memberKey(memberKey)
            .build();
        BDDMockito.given(memberServiceClient.searchMemberSeq())
            .willReturn(ApiResponse.ok(member));
    }

    private void auctionParticipation(int auctionScheduleId, String memberKey, int participationNumber) {
        redisTemplate.opsForHash().put(String.valueOf(auctionScheduleId), memberKey, participationNumber);
    }
}