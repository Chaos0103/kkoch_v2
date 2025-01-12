package com.ssafy.userservice.domain.member.repository;

import com.ssafy.userservice.domain.member.Member;
import com.ssafy.userservice.domain.member.enums.Role;
import com.ssafy.userservice.domain.member.vo.*;
import common.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest extends IntegrationTestSupport {

    private static final String DEFAULT_MEMBER_KEY = UUID.randomUUID().toString();
    private static final String DEFAULT_EMAIL = "ssafy@gmail.com";
    private static final String DEFAULT_TEL = "01012341234";
    private static final String DEFAULT_BUSINESS_NUMBER = "1231212345";

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder encoder;

    @DisplayName("입력 받은 이메일을 사용하는 회원이 존재하면 true를 반환한다.")
    @Test
    void emailExist() {
        //given
        String email = "ssafy@gmail.com";
        createMember(DEFAULT_MEMBER_KEY, email, DEFAULT_TEL, DEFAULT_BUSINESS_NUMBER);

        //when
        boolean result = memberRepository.existsByEmail(Email.of(email));

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("입력 받은 이메일을 사용하는 회원이 존재하지 않으면 false를 반환한다.")
    @Test
    void emailDoesNotExist() {
        //given
        String email = "ssafy@gmail.com";

        //when
        boolean result = memberRepository.existsByEmail(Email.of(email));

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("입력 받은 연락처를 사용하는 회원이 존재하면 true를 반환한다.")
    @Test
    void telExist() {
        //given
        String tel = "01012341234";
        createMember(DEFAULT_MEMBER_KEY, DEFAULT_EMAIL, tel, DEFAULT_BUSINESS_NUMBER);

        //when
        boolean result = memberRepository.existsByTel(Tel.of(tel));

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("입력 받은 연락처를 사용하는 회원이 존재하지 않으면 false를 반환한다.")
    @Test
    void telDoesNotExist() {
        //given
        String tel = "01012341234";

        //when
        boolean result = memberRepository.existsByTel(Tel.of(tel));

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("입력 받은 사업자 번호를 사용하는 회원이 존재하면 true를 반환한다.")
    @Test
    void businessNumberExist() {
        //given
        String businessNumber = "1231212345";
        createMember(DEFAULT_MEMBER_KEY, DEFAULT_EMAIL, DEFAULT_TEL, businessNumber);

        //when
        boolean result = memberRepository.existsByUserAdditionalInfoBusinessNumber(BusinessNumber.of(businessNumber));

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("입력 받은 사업자 번호를 사용하는 회원이 존재하지 않으면 false를 반환한다.")
    @Test
    void businessNumberDoesNotExist() {
        //given
        String businessNumber = "1231212345";

        //when
        boolean result = memberRepository.existsByUserAdditionalInfoBusinessNumber(BusinessNumber.of(businessNumber));

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("입력 받은 회원 고유키와 일치하는 회원을 조회한다.")
    @Test
    void findMemberByMemberKey() {
        //given
        String memberKey = UUID.randomUUID().toString();
        Member createdMember = createMember(memberKey, DEFAULT_EMAIL, DEFAULT_TEL, DEFAULT_BUSINESS_NUMBER);

        //when
        Optional<Member> findMember = memberRepository.findBySpecificInfoMemberKey(memberKey);

        //then
        assertThat(findMember).isPresent()
            .hasValueSatisfying(member -> {
                assertThat(member).isEqualTo(createdMember);
            });
    }

    @DisplayName("입력 받은 회원 고유키와 일치하는 회원이 없을 경우 빈 값을 반환한다.")
    @Test
    void findMemberByInvalidMemberKey() {
        //given
        String memberKey = UUID.randomUUID().toString();

        //when
        Optional<Member> findMember = memberRepository.findBySpecificInfoMemberKey(memberKey);

        //then
        assertThat(findMember).isEmpty();
    }

    @DisplayName("입력 받은 이메일과 일치하는 회원을 조회한다.")
    @Test
    void findMemberByEmail() {
        //given
        String email = "ssafy@gmail.com";
        Member createdMember = createMember(DEFAULT_MEMBER_KEY, email, DEFAULT_TEL, DEFAULT_BUSINESS_NUMBER);

        //when
        Optional<Member> findMember = memberRepository.findByEmail(Email.of(email));

        //then
        assertThat(findMember).isPresent()
            .hasValueSatisfying(member -> {
                assertThat(member).isEqualTo(createdMember);
            });
    }

    @DisplayName("입력 받은 이메일과 일치하는 회원이 없을 경우 빈 값을 반환한다.")
    @Test
    void findMemberByInvalidEmail() {
        //given
        String email = "invalid@gmail.com";

        //when
        Optional<Member> findMember = memberRepository.findByEmail(Email.of(email));

        //then
        assertThat(findMember).isEmpty();
    }

    private Member createMember(String memberKey, String email, String tel, String businessNumber) {
        Member member = Member.builder()
            .isDeleted(false)
            .specificInfo(createSpecificInfo(memberKey))
            .email(Email.of(email))
            .password(Password.of("ssafy1234!"))
            .name(Name.of("김싸피"))
            .tel(Tel.of(tel))
            .userAdditionalInfo(createUserAdditionalInfo(businessNumber))
            .build();
        return memberRepository.save(member);
    }

    private MemberSpecificInfo createSpecificInfo(String memberKey) {
        return MemberSpecificInfo.builder()
            .memberKey(memberKey)
            .role(Role.USER)
            .build();
    }

    private UserAdditionalInfo createUserAdditionalInfo(String businessNumber) {
        return UserAdditionalInfo.builder()
            .businessNumber(BusinessNumber.of(businessNumber))
            .bankAccount(createDefaultBackAccount())
            .build();
    }

    private BankAccount createDefaultBackAccount() {
        return BankAccount.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();
    }
}