package com.ssafy.userservice.api.service.member.response;

import com.ssafy.userservice.domain.member.repository.response.MemberDisplayInfoDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDisplayInfoResponse {

    private final String email;
    private final String name;
    private final String tel;

    @Builder
    private MemberDisplayInfoResponse(String email, String name, String tel) {
        this.email = email;
        this.name = name;
        this.tel = tel;
    }

    public static MemberDisplayInfoResponse of(MemberDisplayInfoDto memberInfo) {
        return MemberDisplayInfoResponse.builder()
            .email(memberInfo.getMaskingEmail())
            .name(memberInfo.getUsername())
            .tel(memberInfo.getMaskingTel())
            .build();
    }
}
