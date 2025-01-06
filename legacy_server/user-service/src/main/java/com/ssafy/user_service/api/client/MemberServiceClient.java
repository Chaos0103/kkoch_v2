package com.ssafy.user_service.api.client;

import com.ssafy.user_service.api.ApiResponse;
import com.ssafy.user_service.api.service.member.MemberQueryService;
import com.ssafy.user_service.common.security.SecurityUtils;
import com.ssafy.user_service.domain.member.repository.response.MemberIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/client")
public class MemberServiceClient {

    private final MemberQueryService memberQueryService;

    @GetMapping
    public ApiResponse<MemberIdResponse> searchMemberId() {
        String memberKey = SecurityUtils.findMemberKeyByToken();

        MemberIdResponse response = memberQueryService.searchMemberId(memberKey);

        return ApiResponse.ok(response);
    }
}
