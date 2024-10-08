package com.ssafy.trade_service;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.client.MemberServiceClient;
import com.ssafy.trade_service.api.client.response.MemberIdResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport {

    @MockBean
    protected MemberServiceClient memberServiceClient;

    @BeforeEach
    void setUp() {
        mockingMemberId();
    }

    private void mockingMemberId() {
        MemberIdResponse memberId = MemberIdResponse.builder()
            .memberId(1L)
            .build();
        ApiResponse<MemberIdResponse> apiResponse = ApiResponse.ok(memberId);

        given(memberServiceClient.searchMemberId())
            .willReturn(apiResponse);
    }
}
