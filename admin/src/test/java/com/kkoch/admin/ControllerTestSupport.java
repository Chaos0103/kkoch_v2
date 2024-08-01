package com.kkoch.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkoch.admin.api.controller.stats.StatsApiController;
import com.kkoch.admin.api.controller.trade.TradeApiController;
import com.kkoch.admin.api.controller.variety.VarietyApiController;
import com.kkoch.admin.api.controller.admin.AdminApiController;
import com.kkoch.admin.api.controller.auctionschedule.AuctionScheduleApiController;
import com.kkoch.admin.api.controller.auctionvariety.AuctionVarietyApiController;
import com.kkoch.admin.api.controller.bidresult.BidResultApiController;
import com.kkoch.admin.api.controller.notice.NoticeApiController;
import com.kkoch.admin.api.service.admin.AdminService;
import com.kkoch.admin.api.service.auctionschedule.AuctionScheduleQueryService;
import com.kkoch.admin.api.service.auctionschedule.AuctionScheduleService;
import com.kkoch.admin.api.service.auctionvariety.AuctionVarietyQueryService;
import com.kkoch.admin.api.service.auctionvariety.AuctionVarietyService;
import com.kkoch.admin.api.service.bidresult.BidResultQueryService;
import com.kkoch.admin.api.service.bidresult.BidResultService;
import com.kkoch.admin.api.service.notice.NoticeQueryService;
import com.kkoch.admin.api.service.notice.NoticeService;
import com.kkoch.admin.api.service.stats.StatsQueryService;
import com.kkoch.admin.api.service.trade.TradeQueryService;
import com.kkoch.admin.api.service.variety.VarietyQueryService;
import com.kkoch.admin.api.service.variety.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    AdminApiController.class,
    NoticeApiController.class,
    AuctionScheduleApiController.class,
    VarietyApiController.class,
    BidResultApiController.class,
    AuctionVarietyApiController.class,
    StatsApiController.class,
    TradeApiController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AdminService adminService;

    @MockBean
    protected NoticeService noticeService;

    @MockBean
    protected NoticeQueryService noticeQueryService;

    @MockBean
    protected AuctionScheduleService auctionScheduleService;

    @MockBean
    protected AuctionScheduleQueryService auctionScheduleQueryService;

    @MockBean
    protected VarietyService varietyService;

    @MockBean
    protected VarietyQueryService varietyQueryService;

    @MockBean
    protected BidResultService bidResultService;

    @MockBean
    protected TradeQueryService tradeQueryService;

    @MockBean
    protected BidResultQueryService bidResultQueryService;

    @MockBean
    protected AuctionVarietyService auctionVarietyService;

    @MockBean
    protected AuctionVarietyQueryService auctionVarietyQueryService;

    @MockBean
    protected StatsQueryService statsQueryService;
}
