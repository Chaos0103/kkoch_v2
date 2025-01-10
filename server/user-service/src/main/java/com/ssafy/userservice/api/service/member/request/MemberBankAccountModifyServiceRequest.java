package com.ssafy.userservice.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberBankAccountModifyServiceRequest {

    private final String bankCode;
    private final String accountNumber;

    @Builder
    private MemberBankAccountModifyServiceRequest(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }
}
