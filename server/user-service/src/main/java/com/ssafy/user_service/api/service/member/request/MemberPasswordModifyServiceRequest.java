package com.ssafy.user_service.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberPasswordModifyServiceRequest {

    private final String currentPassword;
    private final String newPassword;

    @Builder
    private MemberPasswordModifyServiceRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public static MemberPasswordModifyServiceRequest of(String currentPassword, String newPassword) {
        return new MemberPasswordModifyServiceRequest(currentPassword, newPassword);
    }
}
