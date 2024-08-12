package com.ssafy.user_service.api.controller.member.request;

import com.ssafy.user_service.api.service.member.request.MemberTelModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberTelModifyRequest {

    @NotBlank(message = "연락처를 입력해주세요.")
    private String tel;

    @Builder
    private MemberTelModifyRequest(String tel) {
        this.tel = tel;
    }

    public MemberTelModifyServiceRequest toServiceRequest() {
        return MemberTelModifyServiceRequest.of(tel);
    }
}
