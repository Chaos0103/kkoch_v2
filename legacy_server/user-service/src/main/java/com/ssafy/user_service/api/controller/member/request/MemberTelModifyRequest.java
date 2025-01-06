package com.ssafy.user_service.api.controller.member.request;

import com.ssafy.user_service.api.service.member.request.MemberTelModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.user_service.api.controller.member.message.MemberBindingMessage.NOT_BLANK_TEL;

@Getter
@NoArgsConstructor
public class MemberTelModifyRequest {

    @NotBlank(message = NOT_BLANK_TEL)
    private String tel;

    @Builder
    private MemberTelModifyRequest(String tel) {
        this.tel = tel;
    }

    public MemberTelModifyServiceRequest toServiceRequest() {
        return MemberTelModifyServiceRequest.of(tel);
    }
}
