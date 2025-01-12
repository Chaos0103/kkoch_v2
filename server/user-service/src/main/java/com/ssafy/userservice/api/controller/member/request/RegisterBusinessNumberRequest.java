package com.ssafy.userservice.api.controller.member.request;

import com.ssafy.userservice.api.service.member.request.RegisterBusinessNumberServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_BUSINESS_NUMBER;

@Getter
public class RegisterBusinessNumberRequest {

    @NotBlank(message = NOT_BLANK_BUSINESS_NUMBER)
    private final String businessNumber;

    @Builder
    private RegisterBusinessNumberRequest(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public RegisterBusinessNumberServiceRequest toServiceRequest() {
        return RegisterBusinessNumberServiceRequest.of(businessNumber.strip());
    }
}
