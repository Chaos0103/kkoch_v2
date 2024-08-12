package com.ssafy.user_service.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberTelModifyServiceRequest {

    private final String tel;

    @Builder
    private MemberTelModifyServiceRequest(String tel) {
        this.tel = tel;
    }

    public static MemberTelModifyServiceRequest of(String tel) {
        return new MemberTelModifyServiceRequest(tel);
    }
}
