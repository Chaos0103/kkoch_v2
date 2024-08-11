package com.ssafy.user_service.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidateBusinessNumberRequest {

    @NotBlank(message = "사업자 번호를 입력해주세요.")
    private String businessNumber;

    @Builder
    private ValidateBusinessNumberRequest(String businessNumber) {
        this.businessNumber = businessNumber;
    }
}
