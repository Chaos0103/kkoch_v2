package com.ssafy.trade_service.api.controller.order;

import com.ssafy.trade_service.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderApiQueryControllerTest extends ControllerTestSupport {

    @DisplayName("회원의 주문 목록을 조회한다.")
    @Test
    void searchOrders() throws Exception {
        mockMvc.perform(
                get("/trade-service/orders")
            )
            .andExpect(status().isOk());
    }
}