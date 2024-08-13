package com.ssafy.user_service.api.controller.member.request;

import com.ssafy.user_service.api.service.member.request.BankAccountServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.user_service.api.controller.member.message.MemberBindingMessage.NOT_BLANK_ACCOUNT_NUMBER;
import static com.ssafy.user_service.api.controller.member.message.MemberBindingMessage.NOT_BLANK_BANK_CODE;

@Getter
@NoArgsConstructor
public class BankAccountRequest {

    @NotBlank(message = NOT_BLANK_BANK_CODE)
    private String bankCode;

    @NotBlank(message = NOT_BLANK_ACCOUNT_NUMBER)
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
