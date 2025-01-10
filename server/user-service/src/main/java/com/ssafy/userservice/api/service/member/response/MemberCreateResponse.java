package com.ssafy.userservice.api.service.member.response;

import com.ssafy.userservice.domain.member.Member;
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
        return MemberCreateResponse.builder()
            .email(member.getMaskingEmail())
            .name(member.getName())
            .createdDateTime(member.getCreatedDateTime())
            .build();
    }
}
