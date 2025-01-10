package com.ssafy.userservice.api.controller.member;

import com.ssafy.common.api.ApiResponse;
import com.ssafy.userservice.api.service.member.MemberQueryService;
import com.ssafy.userservice.api.service.member.response.MemberDisplayInfoResponse;
import com.ssafy.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberQueryApiController {

    private final MemberQueryService memberQueryService;

    @GetMapping("/account")
    public ApiResponse<MemberDisplayInfoResponse> searchMemberInfo() {
        String memberKey = TokenUtils.getMemberKeyByToken();
        log.debug("회원 기본 정보 조회 요청 [memberKey = {}]", memberKey);

        MemberDisplayInfoResponse response = memberQueryService.searchMemberInfo(memberKey);

        return ApiResponse.ok(response);
    }
}
