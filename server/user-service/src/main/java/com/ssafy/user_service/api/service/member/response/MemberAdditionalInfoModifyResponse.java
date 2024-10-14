package com.ssafy.user_service.api.service.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.ssafy.user_service.api.service.member.Masking.maskingAccountNumber;

@Getter
@NoArgsConstructor
public class MemberAdditionalInfoModifyResponse {

    private String bankCode;
    private String accountNumber;
    private LocalDateTime bankAccountModifiedDateTime;

    @Builder
    private MemberAdditionalInfoModifyResponse(String bankCode, String accountNumber, LocalDateTime bankAccountModifiedDateTime) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.bankAccountModifiedDateTime = bankAccountModifiedDateTime;
    }

    public static MemberAdditionalInfoModifyResponse of(String bankCode, String accountNumber, LocalDateTime bankAccountModifiedDateTime) {
        String maskingAccountNumber = maskingAccountNumber(accountNumber);
        return new MemberAdditionalInfoModifyResponse(bankCode, maskingAccountNumber, bankAccountModifiedDateTime);
    }
}
