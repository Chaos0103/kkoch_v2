package com.ssafy.user_service.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.user_service.api.controller.member.message.MemberBindingMessage.NOT_BLANK_PASSWORD;

@Getter
@NoArgsConstructor
public class MemberRemoveRequest {

    @NotBlank(message = NOT_BLANK_PASSWORD)
    private String password;

    @Builder
    private MemberRemoveRequest(String password) {
        this.password = password;
    }
}
