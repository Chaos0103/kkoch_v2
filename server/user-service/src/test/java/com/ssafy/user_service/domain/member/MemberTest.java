package com.ssafy.user_service.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @DisplayName("일반 회원 엔티티 객체를 생성한다.")
    @Test
    void createUser() {
        //given //when
        Member member = Member.createUser("ssafy@ssafy.com", "ssafy1234!", "김싸피", "01012341234", "1231212345");

        //then
        assertThat(member).isNotNull()
            .hasFieldOrPropertyWithValue("id", null)
            .hasFieldOrPropertyWithValue("specificInfo.role", Role.USER)
            .hasFieldOrPropertyWithValue("email", "ssafy@ssafy.com")
            .hasFieldOrPropertyWithValue("name", "김싸피")
            .hasFieldOrPropertyWithValue("tel", "01012341234")
            .hasFieldOrPropertyWithValue("userAdditionalInfo.businessNumber", "1231212345")
            .hasFieldOrPropertyWithValue("userAdditionalInfo.bankAccount", null)
            .hasFieldOrPropertyWithValue("isDeleted", false);
    }

}