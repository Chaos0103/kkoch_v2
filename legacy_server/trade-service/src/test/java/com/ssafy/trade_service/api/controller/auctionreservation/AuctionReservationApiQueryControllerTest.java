package com.ssafy.trade_service.api.controller.auctionreservation;

import com.ssafy.trade_service.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionReservationApiQueryControllerTest extends ControllerTestSupport {

    @DisplayName("경매 예약 목록을 조회한다.")
    @Test
    void searchAuctionReservations() throws Exception {
        mockMvc.perform(
                get("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations", 1)
            )
            .andExpect(status().isOk());
    }
}