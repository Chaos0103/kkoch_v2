package com.ssafy.user_service.api.controller.member.request;

import com.ssafy.user_service.api.service.member.request.MemberCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.user_service.api.controller.member.message.MemberBindingMessage.*;

@Getter
@NoArgsConstructor
public class AdminMemberCreateRequest {

    @NotBlank(message = NOT_BLANK_EMAIL)
    private String email;

    @NotBlank(message = NOT_BLANK_PASSWORD)
    private String password;

    @NotBlank(message = NOT_BLANK_NAME)
    private String name;

    @NotBlank(message = NOT_BLANK_TEL)
    private String tel;

    @Builder
    private AdminMemberCreateRequest(String email, String password, String name, String tel) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
    }

    public MemberCreateServiceRequest toServiceRequest() {
        return MemberCreateServiceRequest.createAdmin(email, password, name, tel);
    }
}
