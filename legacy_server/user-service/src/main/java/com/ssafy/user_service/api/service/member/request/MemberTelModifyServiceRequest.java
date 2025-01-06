package com.ssafy.user_service.api.service.member.request;

import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.user_service.domain.member.MemberValidate.validateTel;

public class MemberTelModifyServiceRequest {

    private final String tel;

    @Builder
    private MemberTelModifyServiceRequest(String tel) {
        this.tel = tel;
    }

    public static MemberTelModifyServiceRequest of(String tel) {
        validateTel(tel);

        return new MemberTelModifyServiceRequest(tel);
    }

    public void modifyTelOf(Member member) {
        member.modifyTel(tel);
    }

    public boolean isDuplicatedTel(MemberRepository memberRepository) {
        return memberRepository.existsByTel(tel);
    }
}
