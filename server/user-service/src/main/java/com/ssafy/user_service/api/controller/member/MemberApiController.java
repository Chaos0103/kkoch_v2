package com.ssafy.user_service.api.controller.member;

import com.ssafy.user_service.api.ApiResponse;
import com.ssafy.user_service.api.controller.member.request.*;
import com.ssafy.user_service.api.service.member.MemberService;
import com.ssafy.user_service.api.service.member.response.BankAccountAuthResponse;
import com.ssafy.user_service.api.service.member.response.MemberCreateResponse;
import com.ssafy.user_service.api.service.member.response.MemberPasswordModifyResponse;
import com.ssafy.user_service.api.service.member.response.MemberTelModifyResponse;
import com.ssafy.user_service.common.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @PatchMapping("/password")
    public ApiResponse<MemberPasswordModifyResponse> modifyPassword(@Valid @RequestBody MemberPasswordModifyRequest request) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        String memberKey = SecurityUtils.findMemberKeyByToken();

        MemberPasswordModifyResponse response = memberService.modifyPassword(memberKey, currentDateTime, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PatchMapping("/tel")
    public ApiResponse<MemberTelModifyResponse> modifyTel(@Valid @RequestBody MemberTelModifyRequest request) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        String memberKey = SecurityUtils.findMemberKeyByToken();

        MemberTelModifyResponse response = memberService.modifyTel(memberKey, currentDateTime, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/bank-account")
    public ApiResponse<BankAccountAuthResponse> sendOneCoinAuthNumber(@Valid @RequestBody BankAccountRequest request) {
        return null;
    }
}
