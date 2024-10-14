package com.ssafy.user_service.domain.member.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemberSpecificInfoTest {

    @DisplayName("회원 고유키와 권한이 동일하면 동일한 객체이다.")
    @Test
    void equals() {
        String memberKey = UUID.randomUUID().toString();
        MemberSpecificInfo memberSpecificInfo1 = createMemberSpecificInfo(memberKey);
        MemberSpecificInfo memberSpecificInfo2 = createMemberSpecificInfo(memberKey);

        assertThat(memberSpecificInfo1).isEqualTo(memberSpecificInfo2);
    }

    private MemberSpecificInfo createMemberSpecificInfo(String memberKey) {
        return MemberSpecificInfo.builder()
            .memberKey(memberKey)
            .role(Role.USER)
            .build();
    }
}