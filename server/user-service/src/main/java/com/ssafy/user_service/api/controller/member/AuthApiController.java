package com.ssafy.user_service.api.controller.member;

import com.ssafy.user_service.api.ApiResponse;
import com.ssafy.user_service.api.controller.member.request.SendAuthNumberRequest;
import com.ssafy.user_service.api.controller.member.request.ValidateAuthNumberRequest;
import com.ssafy.user_service.api.controller.member.request.ValidateBusinessNumberRequest;
import com.ssafy.user_service.api.controller.member.request.ValidateTelRequest;
import com.ssafy.user_service.api.service.member.AuthService;
import com.ssafy.user_service.api.service.member.response.BusinessNumberValidateResponse;
import com.ssafy.user_service.api.service.member.response.EmailAuthResponse;
import com.ssafy.user_service.api.service.member.response.EmailValidateResponse;
import com.ssafy.user_service.api.service.member.response.TelValidateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.ssafy.user_service.api.controller.member.AuthNumberGenerator.generateEmailAuthNumber;
import static com.ssafy.user_service.common.util.TimeUtils.getCurrentDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/email")
    public ApiResponse<EmailAuthResponse> sendAuthNumber(@Valid @RequestBody SendAuthNumberRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        String authNumber = generateEmailAuthNumber();

        EmailAuthResponse response = authService.sendAuthNumberToEmail(request.getEmail(), authNumber, currentDateTime);

        return ApiResponse.ok(response);
    }

    @PostMapping("/email/validate")
    public ApiResponse<EmailValidateResponse> validateAuthNumber(@Valid @RequestBody ValidateAuthNumberRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        EmailValidateResponse response = authService.validateAuthNumberToEmail(request.getEmail(), request.getAuthNumber(), currentDateTime);

        return ApiResponse.ok(response);
    }

    @PostMapping("/tel/validate")
    public ApiResponse<TelValidateResponse> validateTel(@Valid @RequestBody ValidateTelRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        TelValidateResponse response = authService.validateTel(request.getTel(), currentDateTime);

        return ApiResponse.ok(response);
    }

    @PostMapping("/business-number/validate")
    public ApiResponse<BusinessNumberValidateResponse> validateBusinessNumber(@Valid @RequestBody ValidateBusinessNumberRequest request) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        BusinessNumberValidateResponse response = authService.validateBusinessNumber(request.getBusinessNumber(), currentDateTime);

        return ApiResponse.ok(response);
    }
}
