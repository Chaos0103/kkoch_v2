package com.ssafy.user_service.api.service.member.request;

import com.ssafy.user_service.domain.member.vo.BankAccount;
import com.ssafy.user_service.domain.member.vo.UserAdditionalInfo;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.user_service.domain.member.MemberValidate.*;

@Getter
public class MemberUserAdditionalInfoModifyServiceRequest {

    private final String businessNumber;
    private final String bankCode;
    private final String accountNumber;

    @Builder
    private MemberUserAdditionalInfoModifyServiceRequest(String businessNumber, String bankCode, String accountNumber) {
        this.businessNumber = businessNumber;
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }

    public static MemberUserAdditionalInfoModifyServiceRequest of(String businessNumber, String bankCode, String accountNumber) {
        validateBusinessNumber(businessNumber);
        validateBankCode(bankCode);
        validateAccountNumber(accountNumber);

        return new MemberUserAdditionalInfoModifyServiceRequest(businessNumber, bankCode, accountNumber);
    }

    public UserAdditionalInfo createUserAdditionalInfo() {
        BankAccount bankAccount = BankAccount.of(bankCode, accountNumber);
        return UserAdditionalInfo.of(businessNumber, bankAccount);
    }
}
