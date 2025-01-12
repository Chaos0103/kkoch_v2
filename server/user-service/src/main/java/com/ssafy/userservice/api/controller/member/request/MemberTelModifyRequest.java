package com.ssafy.userservice.api.controller.member.request;

import com.ssafy.userservice.api.service.member.request.MemberTelModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_TEL;

@Getter
public class MemberTelModifyRequest {

    @NotBlank(message = NOT_BLANK_TEL)
    private final String tel;

    @Builder
    private MemberTelModifyRequest(String tel) {
        this.tel = tel;
    }

    public MemberTelModifyServiceRequest toServiceRequest() {
        return MemberTelModifyServiceRequest.of(tel.strip());
    }
}
