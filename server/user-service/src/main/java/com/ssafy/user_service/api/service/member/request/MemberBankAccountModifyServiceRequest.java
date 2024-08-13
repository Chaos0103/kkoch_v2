package com.ssafy.user_service.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

import static com.ssafy.user_service.api.service.member.MemberValidate.validateAccountNumber;
import static com.ssafy.user_service.api.service.member.MemberValidate.validateBankCode;

@Getter
public class MemberBankAccountModifyServiceRequest {

    private final String bankCode;
    private final String accountNumber;

    @Builder
    private MemberBankAccountModifyServiceRequest(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }

    public static MemberBankAccountModifyServiceRequest of(String bankCode, String accountNumber) {
        validateBankCode(bankCode);
        validateAccountNumber(accountNumber);

        return new MemberBankAccountModifyServiceRequest(bankCode, accountNumber);
    }
}
