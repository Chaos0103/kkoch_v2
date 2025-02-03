package com.ssafy.userservice.api.controller.auth;

import com.ssafy.common.api.ApiResponse;
import com.ssafy.userservice.api.controller.AuthenticationNumberGenerator;
import com.ssafy.userservice.api.controller.auth.request.BusinessNumberAuthenticationRequest;
import com.ssafy.userservice.api.controller.auth.request.EmailAuthenticationRequest;
import com.ssafy.userservice.api.controller.auth.request.SendEmailAuthenticationNumberRequest;
import com.ssafy.userservice.api.controller.auth.request.TelAuthenticationRequest;
import com.ssafy.userservice.api.service.auth.AuthenticationService;
import com.ssafy.userservice.api.service.auth.response.BusinessNumberAuthenticationResultResponse;
import com.ssafy.userservice.api.service.auth.response.EmailAuthenticationResponse;
import com.ssafy.userservice.api.service.auth.response.EmailAuthenticationResultResponse;
import com.ssafy.userservice.api.service.auth.response.TelAuthenticationResultResponse;
import com.ssafy.userservice.domain.member.vo.BusinessNumber;
import com.ssafy.userservice.domain.member.vo.Email;
import com.ssafy.userservice.domain.member.vo.Tel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.ssafy.common.global.utils.TimeUtils.getCurrentDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthenticationApiController {

    private final AuthenticationService authenticationService;

    @PostMapping("/email")
    public ApiResponse<EmailAuthenticationResponse> sendAuthenticationNumberToEmail(@Valid @RequestBody SendEmailAuthenticationNumberRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        Email email = Email.of(request.getEmail());

        String issuedAuthenticationNumber = AuthenticationNumberGenerator.generateEmailAuthenticationNumber();

        EmailAuthenticationResponse response = authenticationService.sendAuthenticationNumberToEmail(email, issuedAuthenticationNumber, currentDateTime);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/email")
    public ApiResponse<EmailAuthenticationResultResponse> checkAuthenticationNumberToEmail(@Valid @RequestBody EmailAuthenticationRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        Email email = Email.of(request.getEmail());

        EmailAuthenticationResultResponse response = authenticationService.checkAuthenticationNumberToEmail(email, request.getAuthenticationNumber(), currentDateTime);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/tel")
    public ApiResponse<TelAuthenticationResultResponse> checkTel(@Valid @RequestBody TelAuthenticationRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        Tel tel = Tel.of(request.getTel());

        TelAuthenticationResultResponse response = authenticationService.checkTel(tel, currentDateTime);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/business-number")
    public ApiResponse<BusinessNumberAuthenticationResultResponse> checkBusinessNumber(@Valid @RequestBody BusinessNumberAuthenticationRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        BusinessNumber businessNumber = BusinessNumber.of(request.getBusinessNumber());

        BusinessNumberAuthenticationResultResponse response = authenticationService.checkBusinessNumber(businessNumber, currentDateTime);

        return ApiResponse.ok(response);
    }
}
