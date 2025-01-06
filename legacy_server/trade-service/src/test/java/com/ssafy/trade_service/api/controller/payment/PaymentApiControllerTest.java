package com.ssafy.trade_service.api.controller.payment;

import com.ssafy.trade_service.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentApiControllerTest extends ControllerTestSupport {

    @DisplayName("주문을 결제한다.")
    @Test
    void payment() throws Exception {
        mockMvc.perform(
                post("/trade-service/orders/{orderId}/payment", 1)
            )
            .andExpect(status().isOk());
    }
}