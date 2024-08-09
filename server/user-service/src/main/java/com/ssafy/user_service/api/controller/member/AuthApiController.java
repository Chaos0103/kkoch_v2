package com.ssafy.user_service.api.controller.member;

import com.ssafy.user_service.api.ApiResponse;
import com.ssafy.user_service.api.controller.member.request.SendAuthNumberRequest;
import com.ssafy.user_service.api.service.member.AuthService;
import com.ssafy.user_service.api.service.member.response.EmailAuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/email")
    public ApiResponse<EmailAuthResponse> sendAuthNumber(@Valid @RequestBody SendAuthNumberRequest request) {
        return null;
    }
}
