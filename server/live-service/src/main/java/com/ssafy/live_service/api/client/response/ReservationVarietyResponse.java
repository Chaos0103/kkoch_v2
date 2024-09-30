package com.ssafy.live_service.api.client.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationVarietyResponse {

    private Long id;
    private int desiredPrice;

    private ReservationVarietyResponse(Long id, int desiredPrice) {
        this.id = id;
        this.desiredPrice = desiredPrice;
    }
}
