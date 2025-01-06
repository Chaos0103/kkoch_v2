package com.ssafy.user_service.api.service.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MaskingTest {

    @DisplayName("이메일 ID의 앞 두자리를 제외한 나머지 부분은 마스킹 처리를 한다.")
    @Test
    void maskingEmail() {
        //given
        String email = "ssafy@ssafy.com";

        //when
        String maskingEmail = Masking.maskingEmail(email);

        //then
        assertThat(maskingEmail).isEqualTo("ss***@ssafy.com");
    }
}