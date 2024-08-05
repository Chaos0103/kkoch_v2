package com.ssafy.user_service.api.service.member.request;

import lombok.Builder;

public class MemberCreateServiceRequest {

    private final String email;
    private final String password;
    private final String name;
    private final String tel;
    private final String businessNumber;

    @Builder
    private MemberCreateServiceRequest(String email, String password, String name, String tel, String businessNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
    }
}
