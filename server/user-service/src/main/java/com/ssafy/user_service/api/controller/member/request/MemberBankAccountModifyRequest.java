package com.ssafy.user_service.api.controller.member.request;

import com.ssafy.user_service.api.service.member.request.BankAccountServiceRequest;
import com.ssafy.user_service.api.service.member.request.MemberBankAccountModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberBankAccountModifyRequest {

    @NotBlank(message = "은행 코드를 입력해주세요.")
    private String bankCode;

    @NotBlank(message = "은행 계좌를 입력해주세요.")
    private String accountNumber;

    @NotBlank(message = "인증 번호를 입력해주세요.")
    private String authNumber;

    @Builder
    private MemberBankAccountModifyRequest(String bankCode, String accountNumber, String authNumber) {
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
