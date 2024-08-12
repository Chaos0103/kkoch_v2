package com.ssafy.user_service.api.controller.member.request;

import com.ssafy.user_service.api.service.member.request.BankAccountServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BankAccountRequest {

    @NotBlank(message = "은행 코드를 입력해주세요.")
    private String bankCode;

    @NotBlank(message = "은행 계좌를 입력해주세요.")
    private String accountNumber;

    @Builder
    private BankAccountRequest(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }

    public BankAccountServiceRequest toServiceRequest() {
        return BankAccountServiceRequest.of(bankCode, accountNumber);
    }
}
