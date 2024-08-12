package com.ssafy.user_service.domain.member.repository.response;

import com.ssafy.user_service.api.service.member.Masking;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class MemberInfoResponse {

    private final String email;
    private final String name;
    private final String tel;
    private final String businessNumber;

    @Builder
    private MemberInfoResponse(String email, String name, String tel, String businessNumber) {
        this.email = email;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
    }

    public MemberInfoResponse toMasking() {
        String maskingEmail = Masking.maskingEmail(email);
        String maskingTel = Masking.maskingTel(tel);
        String maskingBusinessNumber = Masking.maskingBusinessNumber(businessNumber);

        return new MemberInfoResponse(maskingEmail, name, maskingTel, maskingBusinessNumber);
    }
}
