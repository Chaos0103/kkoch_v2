package com.ssafy.user_service.api.service.member.request;

import com.ssafy.user_service.domain.member.Member;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ssafy.user_service.domain.member.MemberValidate.validatePassword;

public class MemberPasswordModifyServiceRequest {

    private final String currentPassword;
    private final String newPassword;

    @Builder
    private MemberPasswordModifyServiceRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = validatePassword(newPassword);
    }

    public static MemberPasswordModifyServiceRequest of(String currentPassword, String newPassword) {
        return new MemberPasswordModifyServiceRequest(currentPassword, newPassword);
    }

    public void modifyPwdOf(Member member, PasswordEncoder encoder) {
        member.modifyPassword(encoder.encode(newPassword));
    }

    public boolean isNotMatchesCurrentPwdOf(Member member, PasswordEncoder encoder) {
        return member.isNotMatchesPwd(encoder, currentPassword);
    }
}
