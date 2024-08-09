package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.IntegrationTestSupport;
import com.ssafy.user_service.api.service.member.response.EmailAuthResponse;
import com.ssafy.user_service.common.redis.RedisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AuthServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuthService authService;

    @Autowired
    private RedisRepository<String, String> redisRepository;

    @DisplayName("이메일로 5분간 유효한 랜덤으로 생성된 6자리 숫자로 만들어진 인증 번호를 발송한다.")
    @Test
    void sendEmailAuthNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        //when
        EmailAuthResponse response = authService.sendEmailAuthNumber("ssafy@ssafy.com", "123456", currentDateTime);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("expiredDateTime", LocalDateTime.of(2024, 1, 1, 0, 5, 0));

        String authNumber = redisRepository.findByKey("ssafy@ssafy.com");
        assertThat(authNumber).isEqualTo("123456");
    }

}