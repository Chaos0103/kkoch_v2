package com.ssafy.auction_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.auction_service.api.controller.auctionschedule.AuctionScheduleApiController;
import com.ssafy.auction_service.api.controller.auctionvariety.AuctionVarietyApiController;
import com.ssafy.auction_service.api.controller.variety.VarietyApiController;
import com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleService;
import com.ssafy.auction_service.api.service.auctionvariety.AuctionVarietyService;
import com.ssafy.auction_service.api.service.variety.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    VarietyApiController.class,
    AuctionScheduleApiController.class,
    AuctionVarietyApiController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected VarietyService varietyService;

    @MockBean
    protected AuctionScheduleService auctionScheduleService;

    @MockBean
    protected AuctionVarietyService auctionVarietyService;
}
