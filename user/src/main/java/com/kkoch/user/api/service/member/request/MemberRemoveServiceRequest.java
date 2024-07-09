package com.kkoch.user.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRemoveServiceRequest {

    private final String pwd;

    @Builder
    private MemberRemoveServiceRequest(String pwd) {
        this.pwd = pwd;
    }

    public static MemberRemoveServiceRequest of(String pwd) {
        return new MemberRemoveServiceRequest(pwd);
    }
}
