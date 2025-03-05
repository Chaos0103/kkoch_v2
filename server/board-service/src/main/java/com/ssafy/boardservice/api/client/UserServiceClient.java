package com.ssafy.boardservice.api.client;

import com.ssafy.boardservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserServiceClient {

    @GetMapping("/user-service/v1/client/members/id")
    Long searchMemberId();
}
