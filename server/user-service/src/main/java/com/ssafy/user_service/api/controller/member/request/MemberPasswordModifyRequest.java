package com.ssafy.user_service.api.controller.member.request;

import com.ssafy.user_service.api.service.member.request.MemberPasswordModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.user_service.api.controller.member.message.MemberBindingMessage.NOT_BLANK_CURRENT_PASSWORD;
import static com.ssafy.user_service.api.controller.member.message.MemberBindingMessage.NOT_BLANK_NEW_PASSWORD;

@Getter
@NoArgsConstructor
public class MemberPasswordModifyRequest {

    @NotBlank(message = NOT_BLANK_CURRENT_PASSWORD)
    private String currentPassword;

    @NotBlank(message = NOT_BLANK_NEW_PASSWORD)
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
