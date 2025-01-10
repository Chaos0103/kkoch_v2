package com.ssafy.userservice.api.service.member.response;

import com.ssafy.userservice.domain.member.vo.BankAccount;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberBankAccountModifyResponse {

    private final String bankCode;
    private final String accountNumber;
    private final LocalDateTime bankAccountModifiedDateTime;

    @Builder
    private MemberBankAccountModifyResponse(String bankCode, String accountNumber, LocalDateTime bankAccountModifiedDateTime) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.bankAccountModifiedDateTime = bankAccountModifiedDateTime;
    }

    public static MemberBankAccountModifyResponse of(BankAccount bankAccount, LocalDateTime currentDateTime) {
        return new MemberBankAccountModifyResponse(bankAccount.getBankCode(), bankAccount.getMaskingAccountNumber(), currentDateTime);
    }
}
