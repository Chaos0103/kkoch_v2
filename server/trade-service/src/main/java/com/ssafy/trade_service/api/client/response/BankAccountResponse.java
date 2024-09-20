package com.ssafy.trade_service.api.client.response;

import com.ssafy.trade_service.domain.payment.BankAccount;
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

    public BankAccount toBankAccount() {
        return BankAccount.of(bankCode, accountNumber);
    }
}
