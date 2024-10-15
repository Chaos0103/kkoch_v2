package com.ssafy.user_service.api.service.member.request;

import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import lombok.Builder;

import static com.ssafy.user_service.domain.member.MemberValidate.validateBusinessNumber;

public class RegisterBusinessNumberServiceRequest {

    private final String businessNumber;

    @Builder
    private RegisterBusinessNumberServiceRequest(String businessNumber) {
        this.businessNumber = validateBusinessNumber(businessNumber);
    }

    public static RegisterBusinessNumberServiceRequest of(String businessNumber) {
        return RegisterBusinessNumberServiceRequest.builder()
            .businessNumber(businessNumber)
            .build();
    }

    public void registerBusinessNumber(Member member) {
        member.registerBusinessNumber(businessNumber);
    }

    public boolean isDuplicatedBusinessNumber(MemberRepository memberRepository) {
        return memberRepository.existsByUserAdditionalInfoBusinessNumber(businessNumber);
    }
}
