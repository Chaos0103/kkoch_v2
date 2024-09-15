package com.ssafy.trade_service.api.client;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.client.response.MemberIdResponse;
import com.ssafy.trade_service.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "member-service", configuration = FeignConfig.class)
public interface MemberServiceClient {

    @GetMapping("/member-service/members/client")
    ApiResponse<MemberIdResponse> searchMemberId();
}
