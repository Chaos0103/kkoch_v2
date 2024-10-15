package com.ssafy.user_service.api.service.member.request;

import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.member.vo.BankAccount;
import lombok.Builder;

import static com.ssafy.user_service.domain.member.MemberValidate.*;

public class MemberBankAccountModifyServiceRequest {

    private final String bankCode;
    private final String accountNumber;

    @Builder
    private MemberBankAccountModifyServiceRequest(String bankCode, String accountNumber) {
        this.bankCode = validateBankCode(bankCode);
        this.accountNumber = validateAccountNumber(accountNumber);
    }

    public static MemberBankAccountModifyServiceRequest of(String bankCode, String accountNumber) {
        return new MemberBankAccountModifyServiceRequest(bankCode, accountNumber);
    }

    public BankAccount modifyBankAccountOf(Member member) {
        return member.modifyBankAccount(bankCode, accountNumber);
    }
}
