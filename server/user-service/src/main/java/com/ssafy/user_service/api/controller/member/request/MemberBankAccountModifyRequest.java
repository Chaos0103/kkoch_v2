package com.ssafy.user_service.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberBankAccountModifyRequest {

    private String bankCode;
    private String accountNumber;
    private String authNumber;

    @Builder
    private MemberBankAccountModifyRequest(String bankCode, String accountNumber, String authNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.authNumber = authNumber;
    }
}
