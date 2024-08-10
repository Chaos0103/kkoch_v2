package com.ssafy.user_service.api.controller.member;

import com.ssafy.user_service.api.ApiResponse;
import com.ssafy.user_service.api.controller.member.request.SendAuthNumberRequest;
import com.ssafy.user_service.api.controller.member.request.ValidateAuthNumberRequest;
import com.ssafy.user_service.api.service.member.AuthService;
import com.ssafy.user_service.api.service.member.response.EmailAuthResponse;
import com.ssafy.user_service.api.service.member.response.EmailValidateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.ssafy.user_service.api.controller.member.AuthNumberGenerator.generateAuthNumber;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/email")
    public ApiResponse<EmailAuthResponse> sendAuthNumber(@Valid @RequestBody SendAuthNumberRequest request) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        String authNumber = generateAuthNumber(6);

        EmailAuthResponse response = authService.sendAuthNumberToEmail(request.getEmail(), authNumber, currentDateTime);

        return ApiResponse.ok(response);
    }

    @PostMapping("/email/validate")
    public ApiResponse<EmailValidateResponse> validateAuthNumber(@Valid @RequestBody ValidateAuthNumberRequest request) {
        return null;
    }
}
