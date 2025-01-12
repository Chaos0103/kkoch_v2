package com.ssafy.userservice.api.controller.member;

import com.ssafy.common.api.ApiResponse;
import com.ssafy.userservice.api.controller.AuthenticationNumberGenerator;
import com.ssafy.userservice.api.controller.member.request.*;
import com.ssafy.userservice.api.service.auth.AuthenticationService;
import com.ssafy.userservice.api.service.auth.response.BankAccountAuthenticationResponse;
import com.ssafy.userservice.api.service.member.MemberService;
import com.ssafy.userservice.api.service.member.response.*;
import com.ssafy.userservice.domain.member.vo.BankAccount;
import com.ssafy.userservice.utils.TokenUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.ssafy.common.global.utils.TimeUtils.getCurrentDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberApiController {

    private final MemberService memberService;
    private final AuthenticationService authenticationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberCreateResponse> createMember(@Valid @RequestBody MemberCreateRequest request) {
        log.debug("신규 회원 등록 요청 [email = {}, password = {}, name = {}, tel = {}, role = {}]", request.getEmail(), request.getPassword(), request.getName(), request.getTel(), request.getTel());
        MemberCreateResponse response = memberService.createMember(request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @PatchMapping("/password")
    public ApiResponse<MemberPasswordModifyResponse> modifyPassword(@Valid @RequestBody MemberPasswordModifyRequest request) {
        String memberKey = TokenUtils.getMemberKeyByToken();
        log.debug("회원 비밀번호 수정 요청 [memberKey = {}, currentPassword = {}, newPassword = {}]", memberKey, request.getCurrentPassword(), request.getNewPassword());

        LocalDateTime currentDateTime = getCurrentDateTime();

        MemberPasswordModifyResponse response = memberService.modifyPassword(memberKey, currentDateTime, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PatchMapping("/tel")
    public ApiResponse<MemberTelModifyResponse> modifyTel(@Valid @RequestBody MemberTelModifyRequest request) {
        String memberKey = TokenUtils.getMemberKeyByToken();
        log.debug("회원 연락처 수정 요청 [memberKey = {}, tel = {}]", memberKey, request.getTel());

        LocalDateTime currentDateTime = getCurrentDateTime();

        MemberTelModifyResponse response = memberService.modifyTel(memberKey, currentDateTime, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/business-number")
    public ApiResponse<RegisterBusinessNumberResponse> registerBusinessNumber(@Valid @RequestBody RegisterBusinessNumberRequest request) {
        String memberKey = TokenUtils.getMemberKeyByToken();
        log.debug("회원 사업자 번호 등록 요청 [memberKey = {}, businessNumber = {}]", memberKey, request.getBusinessNumber());

        LocalDateTime currentDateTime = getCurrentDateTime();

        RegisterBusinessNumberResponse response = memberService.registerBusinessNumber(memberKey, currentDateTime, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/bank-account")
    public ApiResponse<BankAccountAuthenticationResponse> sendBankAccountAuthenticationNumber(@Valid @RequestBody SendBankAccountAuthenticationNumberRequest request) {
        String memberKey = TokenUtils.getMemberKeyByToken();
        log.debug("회원 은행 계좌 수정 요청 [memberKey = {}, bankCode = {}, accountNumber = {}]", memberKey, request.getBankCode(), request.getAccountNumber());

        String issuedAuthenticationNumber = AuthenticationNumberGenerator.generateBackAccountAuthenticationNumber();

        LocalDateTime currentDateTime = getCurrentDateTime();

        BankAccount bankAccount = BankAccount.of(request.getBankCode(), request.getAccountNumber());

        BankAccountAuthenticationResponse response = authenticationService.sendAuthenticationNumberToBankAccount(memberKey, bankAccount, issuedAuthenticationNumber, currentDateTime);

        log.debug("발급된 은행 계좌 인증 번호 [issuedAuthenticationNumber = {}]", issuedAuthenticationNumber);
        return ApiResponse.ok(response);
    }

    @PatchMapping("/bank-account")
    public ApiResponse<MemberBankAccountModifyResponse> modifyBankAccount(@Valid @RequestBody MemberBankAccountModifyRequest request) {
        String memberKey = TokenUtils.getMemberKeyByToken();
        log.debug("회원 은행 계좌 수정 요청 [memberKey = {}, authenticationNumber = {}]", memberKey, request.getAuthenticationNumber());

        BankAccount bankAccount = authenticationService.checkAuthenticationNumberToBankAccount(memberKey, request.getAuthenticationNumber());
        log.debug("회원 은행 계좌 정보 [bankCode = {}, accountNumber = {}]", bankAccount.getBankCode(), bankAccount.getAccountNumber());

        LocalDateTime currentDateTime = getCurrentDateTime();

        MemberBankAccountModifyResponse response = memberService.modifyBankAccount(memberKey, currentDateTime, bankAccount);

        return ApiResponse.ok(response);
    }

    @PostMapping("/withdraw")
    public ApiResponse<MemberRemoveResponse> removeMember(@Valid @RequestBody MemberRemoveRequest request) {
        String memberKey = TokenUtils.getMemberKeyByToken();
        log.debug("회원 탈퇴 요청 [memberKey = {}, password = {}]", memberKey, request.getPassword());

        LocalDateTime currentDateTime = getCurrentDateTime();

        MemberRemoveResponse response = memberService.removeMember(memberKey, request.getPassword(), currentDateTime);

        return ApiResponse.ok(response);
    }
}
