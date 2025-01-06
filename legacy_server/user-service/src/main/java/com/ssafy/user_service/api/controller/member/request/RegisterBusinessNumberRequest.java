package com.ssafy.user_service.api.controller.member.request;

import com.ssafy.user_service.api.service.member.request.RegisterBusinessNumberServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.user_service.api.controller.member.message.MemberBindingMessage.NOT_BLANK_BUSINESS_NUMBER;

@Getter
@NoArgsConstructor
public class RegisterBusinessNumberRequest {

    @NotBlank(message = NOT_BLANK_BUSINESS_NUMBER)
    private String businessNumber;

    @Builder
    private RegisterBusinessNumberRequest(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public RegisterBusinessNumberServiceRequest toServiceRequest() {
        return RegisterBusinessNumberServiceRequest.of(businessNumber.strip());
    }
}
