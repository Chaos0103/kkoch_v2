package com.ssafy.user_service.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

import static com.ssafy.user_service.domain.member.MemberValidate.validatePassword;

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
        validatePassword(newPassword);

        return new MemberPasswordModifyServiceRequest(currentPassword, newPassword);
    }
}
