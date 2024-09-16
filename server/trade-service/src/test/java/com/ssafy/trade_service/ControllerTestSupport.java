package com.ssafy.trade_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trade_service.api.controller.auctionreservation.AuctionReservationApiController;
import com.ssafy.trade_service.api.controller.auctionreservation.AuctionReservationApiQueryController;
import com.ssafy.trade_service.api.service.auctionreservation.AuctionReservationQueryService;
import com.ssafy.trade_service.api.service.auctionreservation.AuctionReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    AuctionReservationApiController.class, AuctionReservationApiQueryController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuctionReservationService auctionReservationService;

    @MockBean
    protected AuctionReservationQueryService auctionReservationQueryService;
}
