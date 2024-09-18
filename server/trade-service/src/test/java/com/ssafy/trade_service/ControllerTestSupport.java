package com.ssafy.trade_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trade_service.api.controller.auctionreservation.AuctionReservationApiController;
import com.ssafy.trade_service.api.controller.auctionreservation.AuctionReservationApiQueryController;
import com.ssafy.trade_service.api.controller.order.OrderApiController;
import com.ssafy.trade_service.api.controller.order.OrderApiQueryController;
import com.ssafy.trade_service.api.service.auctionreservation.AuctionReservationQueryService;
import com.ssafy.trade_service.api.service.auctionreservation.AuctionReservationService;
import com.ssafy.trade_service.api.service.order.OrderQueryService;
import com.ssafy.trade_service.api.service.order.OrderService;
import com.ssafy.trade_service.domain.bidinfo.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    AuctionReservationApiController.class, AuctionReservationApiQueryController.class,
    OrderApiController.class, OrderApiQueryController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private BidRepository bidRepository;

    @MockBean
    protected AuctionReservationService auctionReservationService;

    @MockBean
    protected AuctionReservationQueryService auctionReservationQueryService;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected OrderQueryService orderQueryService;

}
