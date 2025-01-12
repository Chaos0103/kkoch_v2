package com.ssafy.userservice.api.controller.member.request;

import com.ssafy.userservice.api.service.member.request.MemberPasswordModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_CURRENT_PASSWORD;
import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_NEW_PASSWORD;

@Getter
public class MemberPasswordModifyRequest {

    @NotBlank(message = NOT_BLANK_CURRENT_PASSWORD)
    private final String currentPassword;

    @NotBlank(message = NOT_BLANK_NEW_PASSWORD)
    private final String newPassword;

    @Builder
    private MemberPasswordModifyRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public MemberPasswordModifyServiceRequest toServiceRequest() {
        return MemberPasswordModifyServiceRequest.of(currentPassword.strip(), newPassword.strip());
    }
}
