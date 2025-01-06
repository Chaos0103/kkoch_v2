package com.ssafy.live_service.api.client;

import com.ssafy.live_service.api.ApiResponse;
import com.ssafy.live_service.api.client.response.MemberResponse;
import com.ssafy.live_service.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "member-service", configuration = FeignConfig.class)
public interface MemberServiceClient {

    @GetMapping("/member-service/members/client")
    ApiResponse<MemberResponse> searchMemberSeq();
}
