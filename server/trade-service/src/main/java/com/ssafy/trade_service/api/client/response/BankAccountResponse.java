package com.ssafy.trade_service.api.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BankAccountResponse {

    private String bankCode;
    private String accountNumber;

    @Builder
    private BankAccountResponse(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }
}
