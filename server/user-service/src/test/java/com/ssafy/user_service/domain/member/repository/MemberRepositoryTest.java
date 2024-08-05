package com.ssafy.user_service.domain.member.repository;

import com.ssafy.user_service.IntegrationTestSupport;
import com.ssafy.user_service.domain.member.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일을 입력 받아 존재 여부를 확인한다.")
    @Test
    void existsByEmail() {
        //given
        Member member = generateMember();

        //when
        boolean isExistEmail = memberRepository.existsByEmail("ssafy@ssafy.com");

        //then
        assertThat(isExistEmail).isTrue();
    }

    @DisplayName("연락처를 입력 받아 존재 여부를 확인한다.")
    @Test
    void existsByTel() {
        //given
        Member member = generateMember();

        //when
        boolean isExistTel = memberRepository.existsByTel("01012341234");

        //then
        assertThat(isExistTel).isTrue();
    }

    @DisplayName("사업자 번호를 입력 받아 존재 여부를 확인한다.")
    @Test
    void existsByUserAdditionalInfoBusinessNumber() {
        //given
        Member member = generateMember();

        //when
        boolean isExistBusinessNumber = memberRepository.existsByUserAdditionalInfoBusinessNumber("1231212345");

        //then
        assertThat(isExistBusinessNumber).isTrue();
    }

    private Member generateMember() {
        Member member = Member.builder()
            .isDeleted(false)
            .specificInfo(MemberSpecificInfo.builder()
                .memberKey(generateMemberKey())
                .role(Role.USER)
                .build())
            .email("ssafy@ssafy.com")
            .pwd(passwordEncoder.encode("ssafy1234!"))
            .name("김싸피")
            .tel("01012341234")
            .userAdditionalInfo(UserAdditionalInfo.builder()
                .businessNumber("1231212345")
                .bankAccount(BankAccount.builder()
                    .bankCode("088")
                    .accountNumber("123123123456")
                    .build())
                .build())
            .build();
        return memberRepository.save(member);
    }
}