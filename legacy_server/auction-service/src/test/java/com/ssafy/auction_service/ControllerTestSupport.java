package com.ssafy.auction_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.auction_service.api.controller.auctionschedule.AuctionScheduleApiController;
import com.ssafy.auction_service.api.controller.auctionschedule.AuctionScheduleApiQueryController;
import com.ssafy.auction_service.api.controller.auctionvariety.AuctionVarietyApiController;
import com.ssafy.auction_service.api.controller.auctionvariety.AuctionVarietyApiQueryController;
import com.ssafy.auction_service.api.controller.variety.VarietyApiController;
import com.ssafy.auction_service.api.controller.variety.VarietyApiQueryController;
import com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleQueryService;
import com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleService;
import com.ssafy.auction_service.api.service.auctionvariety.AuctionVarietyQueryService;
import com.ssafy.auction_service.api.service.auctionvariety.AuctionVarietyService;
import com.ssafy.auction_service.api.service.variety.VarietyQueryService;
import com.ssafy.auction_service.api.service.variety.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    VarietyApiController.class, VarietyApiQueryController.class,
    AuctionScheduleApiController.class, AuctionScheduleApiQueryController.class,
    AuctionVarietyApiController.class, AuctionVarietyApiQueryController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected VarietyService varietyService;

    @MockBean
    protected VarietyQueryService varietyQueryService;

    @MockBean
    protected AuctionScheduleService auctionScheduleService;

    @MockBean
    protected AuctionScheduleQueryService auctionScheduleQueryService;

    @MockBean
    protected AuctionVarietyService auctionVarietyService;

    @MockBean
    protected AuctionVarietyQueryService auctionVarietyQueryService;
}
