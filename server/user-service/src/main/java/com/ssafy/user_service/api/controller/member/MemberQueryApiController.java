package com.ssafy.user_service.api.controller.member;

import com.ssafy.user_service.api.ApiResponse;
import com.ssafy.user_service.api.service.member.MemberQueryService;
import com.ssafy.user_service.common.security.SecurityUtils;
import com.ssafy.user_service.domain.member.repository.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberQueryApiController {

    private final MemberQueryService memberQueryService;

    @GetMapping("/info")
    public ApiResponse<MemberInfoResponse> searchMemberInfo() {
        String memberKey = SecurityUtils.findMemberKeyByToken();

        MemberInfoResponse response = memberQueryService.searchMemberInfo(memberKey);

        return ApiResponse.ok(response);
    }
}
