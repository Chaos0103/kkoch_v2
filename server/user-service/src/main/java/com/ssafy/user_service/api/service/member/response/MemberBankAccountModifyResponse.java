package com.ssafy.user_service.api.service.member.response;

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
        this.accountNumber = accountNumber;
        this.bankAccountModifiedDateTime = bankAccountModifiedDateTime;
    }

    public static MemberBankAccountModifyResponse of(String bankCode, String accountNumber, LocalDateTime bankAccountModifiedDateTime) {
        return new MemberBankAccountModifyResponse(bankCode, accountNumber, bankAccountModifiedDateTime);
    }
}
