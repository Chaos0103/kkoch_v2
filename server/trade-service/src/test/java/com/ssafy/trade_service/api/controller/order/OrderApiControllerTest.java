package com.ssafy.trade_service.api.controller.order;

import com.ssafy.trade_service.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderApiControllerTest extends ControllerTestSupport {

    @DisplayName("주문한 상품을 픽업한다.")
    @Test
    void pickUp() throws Exception {
        mockMvc.perform(
                post("/trade-service/orders/{orderId}/pickup", 1)
            )
            .andExpect(status().isOk());
    }
}