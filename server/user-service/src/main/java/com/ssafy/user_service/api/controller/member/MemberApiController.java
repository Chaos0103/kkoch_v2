package com.ssafy.user_service.api.controller.member;

import com.ssafy.user_service.api.ApiResponse;
import com.ssafy.user_service.api.controller.member.request.*;
import com.ssafy.user_service.api.service.member.AuthService;
import com.ssafy.user_service.api.service.member.MemberService;
import com.ssafy.user_service.api.service.member.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.ssafy.user_service.api.controller.member.AuthNumberGenerator.generateBackAccountAuthNumber;
import static com.ssafy.user_service.common.security.SecurityUtils.findMemberKeyByToken;
import static com.ssafy.user_service.common.util.TimeUtils.getCurrentDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberCreateResponse> createMember(@Valid @RequestBody MemberCreateRequest request) {
        MemberCreateResponse response = memberService.createMember(request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PatchMapping("/password")
    public ApiResponse<MemberPasswordModifyResponse> modifyPassword(@Valid @RequestBody MemberPasswordModifyRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        String memberKey = findMemberKeyByToken();

        MemberPasswordModifyResponse response = memberService.modifyPassword(memberKey, currentDateTime, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PatchMapping("/tel")
    public ApiResponse<MemberTelModifyResponse> modifyTel(@Valid @RequestBody MemberTelModifyRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        String memberKey = findMemberKeyByToken();

        MemberTelModifyResponse response = memberService.modifyTel(memberKey, currentDateTime, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/additional-info")
    public ApiResponse<BankAccountAuthResponse> sendOneCoinAuthNumber(@Valid @RequestBody BankAccountRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        String authNumber = generateBackAccountAuthNumber();

        BankAccountAuthResponse response = authService.sendAuthNumberToBankAccount(request.toServiceRequest(), authNumber, currentDateTime);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/additional-info")
    public ApiResponse<MemberAdditionalInfoModifyResponse> modifyBankAccount(@Valid @RequestBody MemberAdditionalInfoModifyRequest request) {
        authService.validateAuthNumberToBankAccount(request.toAuthServiceRequest(), request.getAuthNumber());

        LocalDateTime currentDateTime = getCurrentDateTime();

        String memberKey = findMemberKeyByToken();

        MemberAdditionalInfoModifyResponse response = memberService.modifyUserAdditionalInfo(memberKey, currentDateTime, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/withdraw")
    public ApiResponse<MemberRemoveResponse> removeMember(@Valid @RequestBody MemberRemoveRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        String memberKey = findMemberKeyByToken();

        MemberRemoveResponse response = memberService.removeMember(memberKey, request.getPassword(), currentDateTime);

        return ApiResponse.ok(response);
    }
}
