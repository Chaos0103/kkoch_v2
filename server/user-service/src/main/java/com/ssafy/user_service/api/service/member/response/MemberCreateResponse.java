package com.ssafy.user_service.api.service.member.response;

import com.ssafy.user_service.api.service.member.Masking;
import com.ssafy.user_service.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberCreateResponse {

    private final String email;
    private final String name;
    private final LocalDateTime createdDateTime;

    @Builder
    private MemberCreateResponse(String email, String name, LocalDateTime createdDateTime) {
        this.email = email;
        this.name = name;
        this.createdDateTime = createdDateTime;
    }

    public static MemberCreateResponse of(Member member) {
        String maskingEmail = Masking.maskingEmail(member.getEmail());
        return new MemberCreateResponse(maskingEmail, member.getName(), member.getCreatedDateTime());
    }
}
