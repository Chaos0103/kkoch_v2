package com.ssafy.user_service.api.service.member.response;

import com.ssafy.user_service.api.service.member.Masking;
import com.ssafy.user_service.domain.member.vo.BankAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberBankAccountModifyResponse {

    private String bankCode;
    private String accountNumber;
    private LocalDateTime bankAccountModifiedDateTime;

    @Builder
    private MemberBankAccountModifyResponse(String bankCode, String accountNumber, LocalDateTime bankAccountModifiedDateTime) {
        this.bankCode = bankCode;
        this.accountNumber = Masking.maskingAccountNumber(accountNumber);
        this.bankAccountModifiedDateTime = bankAccountModifiedDateTime;
    }

    public static MemberBankAccountModifyResponse of(BankAccount bankAccount, LocalDateTime currentDateTime) {
        return MemberBankAccountModifyResponse.builder()
            .bankCode(bankAccount.getBankCode())
            .accountNumber(bankAccount.getAccountNumber())
            .bankAccountModifiedDateTime(currentDateTime)
            .build();
    }
}
