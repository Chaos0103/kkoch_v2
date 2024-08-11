package com.ssafy.user_service.api.controller.member;

import com.ssafy.user_service.api.ApiResponse;
import com.ssafy.user_service.api.controller.member.request.AdminMemberCreateRequest;
import com.ssafy.user_service.api.controller.member.request.MemberCreateRequest;
import com.ssafy.user_service.api.controller.member.request.MemberPasswordModifyRequest;
import com.ssafy.user_service.api.service.member.MemberService;
import com.ssafy.user_service.api.service.member.response.MemberCreateResponse;
import com.ssafy.user_service.api.service.member.response.MemberPasswordModifyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberCreateResponse> createUserMember(@Valid @RequestBody MemberCreateRequest request) {
        MemberCreateResponse response = memberService.createUserMember(request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberCreateResponse> createAdminMember(@Valid @RequestBody AdminMemberCreateRequest request) {
        MemberCreateResponse response = memberService.createAdminMember(request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PatchMapping
    public ApiResponse<MemberPasswordModifyResponse> modifyPassword(@Valid @RequestBody MemberPasswordModifyRequest request) {
        return null;
    }
}
