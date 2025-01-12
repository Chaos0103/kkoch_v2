package com.ssafy.userservice.api.service.member.request;

import com.ssafy.userservice.domain.member.vo.Tel;
import lombok.Builder;

public class MemberTelModifyServiceRequest {

    private final String tel;

    @Builder
    private MemberTelModifyServiceRequest(String tel) {
        this.tel = tel;
    }

    public static MemberTelModifyServiceRequest of(String tel) {
        return new MemberTelModifyServiceRequest(tel);
    }

    public Tel getTel() {
        return Tel.of(tel);
    }
}
