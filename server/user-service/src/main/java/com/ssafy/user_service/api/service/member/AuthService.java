package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.api.service.member.response.EmailAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    public EmailAuthResponse sendEmailAuthNumber(String email, String authNumber, LocalDateTime currentDateTime) {
        return null;
    }
}
