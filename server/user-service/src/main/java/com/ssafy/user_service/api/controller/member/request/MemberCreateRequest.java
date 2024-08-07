package com.ssafy.user_service.api.controller.member.request;

import com.ssafy.user_service.api.service.member.request.MemberCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.user_service.api.controller.member.message.MemberBindingMessage.*;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank(message = NOT_BLANK_EMAIL)
    private String email;

    @NotBlank(message = NOT_BLANK_PASSWORD)
    private String password;

    @NotBlank(message = NOT_BLANK_NAME)
    private String name;

    @NotBlank(message = NOT_BLANK_TEL)
    private String tel;

    @NotBlank(message = NOT_BLANK_BUSINESS_NUMBER)
    private String businessNumber;

    @Builder
    private MemberCreateRequest(String email, String password, String name, String tel, String businessNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
    }

    public MemberCreateServiceRequest toServiceRequest() {
        return MemberCreateServiceRequest.of(email, password, name, tel, businessNumber);
    }
}
