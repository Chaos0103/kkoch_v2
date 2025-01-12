package com.ssafy.userservice.api.service.member.request;

import com.ssafy.userservice.domain.member.vo.BusinessNumber;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterBusinessNumberServiceRequest {

    private final String businessNumber;

    @Builder
    private RegisterBusinessNumberServiceRequest(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public static RegisterBusinessNumberServiceRequest of(String businessNumber) {
        return new RegisterBusinessNumberServiceRequest(businessNumber);
    }

    public BusinessNumber getBusinessNumber() {
        return BusinessNumber.of(businessNumber);
    }
}
