package com.kkoch.user.api.controller.member.response;

import com.kkoch.user.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {

    private final String memberKey;
    private final String email;
    private final String name;

    @Builder
    private MemberResponse(String memberKey, String email, String name) {
        this.memberKey = memberKey;
        this.email = email;
        this.name = name;
    }

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
            .memberKey(member.getMemberKey())
            .email(member.getEmail())
            .name(member.getName())
            .build();
    }
}
