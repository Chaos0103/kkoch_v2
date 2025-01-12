package com.ssafy.userservice.api.controller.member.request;

import com.ssafy.userservice.api.service.member.request.MemberCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.*;

@Getter
public class MemberCreateRequest {

    @NotBlank(message = NOT_BLANK_EMAIL)
    private final String email;

    @NotBlank(message = NOT_BLANK_PASSWORD)
    private final String password;

    @NotBlank(message = NOT_BLANK_NAME)
    private final String name;

    @NotBlank(message = NOT_BLANK_TEL)
    private final String tel;

    @NotBlank(message = NOT_BLANK_ROLE)
    private final String role;

    @Builder
    private MemberCreateRequest(String email, String password, String name, String tel, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.role = role;
    }

    public MemberCreateServiceRequest toServiceRequest() {
        return MemberCreateServiceRequest.of(email.strip(), password.strip(), name.strip(), tel.strip(), role.strip());
    }
}
