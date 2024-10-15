package com.ssafy.user_service.api.controller.member.request;

import com.ssafy.user_service.api.service.member.request.BankAccountServiceRequest;
import com.ssafy.user_service.api.service.member.request.MemberBankAccountModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.user_service.api.controller.member.message.MemberBindingMessage.*;

@Getter
@NoArgsConstructor
public class MemberAdditionalInfoModifyRequest {

    @NotBlank(message = NOT_BLANK_BUSINESS_NUMBER)
    private String businessNumber;

    @NotBlank(message = NOT_BLANK_BANK_CODE)
    private String bankCode;

    @NotBlank(message = NOT_BLANK_ACCOUNT_NUMBER)
    private String accountNumber;

    @NotBlank(message = NOT_BLANK_AUTH_NUMBER)
    private String authNumber;

    @Builder
    private MemberAdditionalInfoModifyRequest(String businessNumber, String bankCode, String accountNumber, String authNumber) {
        this.businessNumber = businessNumber;
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.authNumber = authNumber;
    }

    public BankAccountServiceRequest toAuthServiceRequest() {
        return BankAccountServiceRequest.of(bankCode, accountNumber);
    }

    public MemberBankAccountModifyServiceRequest toServiceRequest() {
        return MemberBankAccountModifyServiceRequest.of(bankCode, accountNumber);
    }
}
