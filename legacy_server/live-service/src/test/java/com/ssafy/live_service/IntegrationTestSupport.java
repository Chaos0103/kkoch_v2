package com.ssafy.live_service;

import com.ssafy.live_service.api.client.MemberServiceClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public abstract class IntegrationTestSupport {

    @MockBean
    protected MemberServiceClient memberServiceClient;
}
