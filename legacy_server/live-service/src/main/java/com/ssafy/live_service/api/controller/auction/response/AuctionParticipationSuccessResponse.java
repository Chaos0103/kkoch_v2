package com.ssafy.live_service.api.controller.auction.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionParticipationSuccessResponse {

    private int generatedParticipationNumber;

    @Builder
    private AuctionParticipationSuccessResponse(int generatedParticipationNumber) {
        this.generatedParticipationNumber = generatedParticipationNumber;
    }

    public static AuctionParticipationSuccessResponse of(int generatedParticipationNumber) {
        return new AuctionParticipationSuccessResponse(generatedParticipationNumber);
    }
}
