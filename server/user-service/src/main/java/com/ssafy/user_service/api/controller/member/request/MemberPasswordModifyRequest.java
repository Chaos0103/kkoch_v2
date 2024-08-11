package com.ssafy.user_service.api.controller.member.request;

import com.ssafy.user_service.api.service.member.request.MemberPasswordModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberPasswordModifyRequest {

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword;

    @NotBlank(message = "신규 비밀번호를 입력해주세요.")
    private String newPassword;

    @Builder
    private MemberPasswordModifyRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public MemberPasswordModifyServiceRequest toServiceRequest() {
        return MemberPasswordModifyServiceRequest.of(currentPassword, newPassword);
    }
}
