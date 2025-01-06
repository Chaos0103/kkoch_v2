package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.IntegrationTestSupport;
import com.ssafy.user_service.api.service.member.request.*;
import com.ssafy.user_service.api.service.member.response.*;
import com.ssafy.user_service.common.exception.AppException;
import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import com.ssafy.user_service.domain.member.vo.BankAccount;
import com.ssafy.user_service.domain.member.vo.MemberSpecificInfo;
import com.ssafy.user_service.domain.member.vo.Role;
import com.ssafy.user_service.domain.member.vo.UserAdditionalInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 등록시 입력 받은 이메일을 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void createMemberDuplicatedEmail() {
        //given
        String email = "ssafy@ssafy.com";
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("1231212345");
        createMember(generateMemberKey(), email, "01012341234", userAdditionalInfo);

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email(email)
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01056785678")
            .role("USER")
            .build();

        //when
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 가입된 이메일입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("회원 등록시 입력 받은 연락처를 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void createMemberDuplicatedTel() {
        //given
        String tel = "01012341234";
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("1231212345");
        createMember(generateMemberKey(), "ssafy@ssafy.com", tel, userAdditionalInfo);

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@gmail.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel(tel)
            .role("USER")
            .build();

        //when
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 가입된 연락처입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("회원 정보를 입력 받아 회원 등록을 한다.")
    @Test
    void createMember() {
        //given
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .role("USER")
            .build();

        //when
        MemberCreateResponse response = memberService.createMember(request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("email", "ss***@ssafy.com")
            .hasFieldOrPropertyWithValue("name", "김싸피");
    }

    @DisplayName("비밀번호 수정시 현재 비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void modifyPasswordNotMatchCurrentPassword() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String memberKey = generateMemberKey();
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("1231212345");
        Member member = createMember(memberKey, "ssafy@ssafy.com", "01012341234", userAdditionalInfo);

        MemberPasswordModifyServiceRequest request = MemberPasswordModifyServiceRequest.builder()
            .currentPassword("ssafy1111!")
            .newPassword("ssafy5678@")
            .build();

        //when
        assertThatThrownBy(() -> memberService.modifyPassword(memberKey, currentDateTime, request))
            .isInstanceOf(AppException.class)
            .hasMessage("비밀번호가 일치하지 않습니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent();

        boolean matches = passwordEncoder.matches("ssafy1234!", findMember.get().getPwd());
        assertThat(matches).isTrue();
    }

    @DisplayName("비밀번호 정보를 입력 받아 비밀번호 수정을 한다.")
    @Test
    void modifyPassword() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String memberKey = generateMemberKey();
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("1231212345");
        Member member = createMember(memberKey, "ssafy@ssafy.com", "01012341234", userAdditionalInfo);

        MemberPasswordModifyServiceRequest request = MemberPasswordModifyServiceRequest.builder()
            .currentPassword("ssafy1234!")
            .newPassword("ssafy5678@")
            .build();

        //when
        MemberPasswordModifyResponse response = memberService.modifyPassword(memberKey, currentDateTime, request);

        //then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent();

        boolean matches = passwordEncoder.matches("ssafy5678@", findMember.get().getPwd());
        assertThat(matches).isTrue();

        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("passwordModifiedDateTime", currentDateTime);
    }

    @DisplayName("연락처 수정시 입력 받은 연락처를 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void modifyTelDuplicatedTel() {
        //given
        UserAdditionalInfo userAdditionalInfo1 = createUserAdditionalInfo("1231212345");
        createMember(generateMemberKey(), "other@ssafy.com", "01012341234", userAdditionalInfo1);

        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String memberKey = generateMemberKey();
        UserAdditionalInfo userAdditionalInfo2 = createUserAdditionalInfo("1112233333");
        Member member = createMember(memberKey, "ssafy@ssafy.com", "01056785678", userAdditionalInfo2);

        MemberTelModifyServiceRequest request = MemberTelModifyServiceRequest.builder()
            .tel("01012341234")
            .build();

        //when
        assertThatThrownBy(() -> memberService.modifyTel(memberKey, currentDateTime, request))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 가입된 연락처입니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("tel", "01056785678");
    }

    @DisplayName("연락처 정보를 입력 받아 연락처 수정을 한다.")
    @Test
    void modifyTel() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String memberKey = generateMemberKey();
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("1231212345");
        Member member = createMember(memberKey, "ssafy@ssafy.com", "01012341234", userAdditionalInfo);

        MemberTelModifyServiceRequest request = MemberTelModifyServiceRequest.builder()
            .tel("01056785678")
            .build();

        //when
        MemberTelModifyResponse response = memberService.modifyTel(memberKey, currentDateTime, request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("modifiedTel", "01056785678")
            .hasFieldOrPropertyWithValue("telModifiedDateTime", currentDateTime);

        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("tel", "01056785678");
    }

    @DisplayName("사업자 번호 등록시 입력 받은 사업자 번호를 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void registerBusinessNumberDuplicatedBusinessNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("1231212345");
        createMember(generateMemberKey(), "ssafy@ssafy.com", "01012341234", userAdditionalInfo);

        String memberKey = generateMemberKey();
        Member member = createMember(memberKey, "ssafy@gmail.com", "01056785678", null);

        RegisterBusinessNumberServiceRequest request = RegisterBusinessNumberServiceRequest.builder()
            .businessNumber("1231212345")
            .build();

        //when
        assertThatThrownBy(() -> memberService.registerBusinessNumber(memberKey, currentDateTime, request))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 가입된 사업자 번호입니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("userAdditionalInfo", null);
    }

    @DisplayName("사업자 번호 등록시 이미 사업자 번호를 등록한 회원이라면 예외가 발생한다.")
    @Test
    void registerBusinessNumberExistBusinessNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String memberKey = generateMemberKey();
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("1231212345");
        Member member = createMember(memberKey, "ssafy@ssafy.com", "01012341234", userAdditionalInfo);

        RegisterBusinessNumberServiceRequest request = RegisterBusinessNumberServiceRequest.builder()
            .businessNumber("1112233333")
            .build();

        //when
        assertThatThrownBy(() -> memberService.registerBusinessNumber(memberKey, currentDateTime, request))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 사업자 번호가 등록되었습니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("userAdditionalInfo.businessNumber", "1231212345");
    }

    @DisplayName("사업자 번호를 입력 받아 사업자 번호를 등록한다.")
    @Test
    void registerBusinessNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String memberKey = generateMemberKey();
        Member member = createMember(memberKey, "ssafy@ssafy.com", "01012341234", null);

        RegisterBusinessNumberServiceRequest request = RegisterBusinessNumberServiceRequest.builder()
            .businessNumber("1231212345")
            .build();

        //when
        RegisterBusinessNumberResponse response = memberService.registerBusinessNumber(memberKey, currentDateTime, request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("businessNumber", "1231212345")
            .hasFieldOrPropertyWithValue("registeredDateTime", currentDateTime);

        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("specificInfo.memberKey", memberKey)
            .hasFieldOrPropertyWithValue("specificInfo.role", Role.BUSINESS)
            .hasFieldOrPropertyWithValue("userAdditionalInfo.businessNumber", "1231212345")
            .hasFieldOrPropertyWithValue("userAdditionalInfo.bankAccount", null);
    }

    @DisplayName("은행 계좌 정보를 입력 받아 은행 계좌 수정을 한다.")
    @Test
    void modifyBankAccount() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("1231212345");
        Member member = createMember(generateMemberKey(), "ssafy@ssafy.com", "01012341234", userAdditionalInfo);

        MemberBankAccountModifyServiceRequest request = MemberBankAccountModifyServiceRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        //when
        MemberBankAccountModifyResponse response = memberService.modifyBankAccount(member.getMemberKey(), currentDateTime, request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("bankCode", "088")
            .hasFieldOrPropertyWithValue("accountNumber", "123***123456")
            .hasFieldOrPropertyWithValue("bankAccountModifiedDateTime", currentDateTime);

        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("userAdditionalInfo.bankAccount.bankCode", "088")
            .hasFieldOrPropertyWithValue("userAdditionalInfo.bankAccount.accountNumber", "123123123456");
    }

    @DisplayName("회원 삭제시 비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void removeMemberNotMatchesPassword() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String memberKey = generateMemberKey();
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("1231212345");
        Member member = createMember(memberKey, "ssafy@ssafy.com", "01012341234", userAdditionalInfo);

        //when
        assertThatThrownBy(() -> memberService.removeMember(memberKey, "ssafy5678@", currentDateTime))
            .isInstanceOf(AppException.class)
            .hasMessage("비밀번호가 일치하지 않습니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", false);
    }

    @DisplayName("회원 정보를 입력 받아 회원 삭제를 한다.")
    @Test
    void removeMember() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("1231212345");
        Member member = createMember(generateMemberKey(), "ssafy@ssafy.com", "01012341234", userAdditionalInfo);

        //when
        MemberRemoveResponse response = memberService.removeMember(member.getMemberKey(), "ssafy1234!", currentDateTime);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("withdrawnDateTime", currentDateTime);

        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", true);
    }

    private Member createMember(String memberKey, String email, String tel, UserAdditionalInfo userAdditionalInfo) {
        Member member = Member.builder()
            .isDeleted(false)
            .specificInfo(MemberSpecificInfo.builder()
                .memberKey(memberKey)
                .role(Role.USER)
                .build())
            .email(email)
            .pwd(passwordEncoder.encode("ssafy1234!"))
            .name("김싸피")
            .tel(tel)
            .userAdditionalInfo(userAdditionalInfo)
            .build();
        return memberRepository.save(member);
    }

    private static UserAdditionalInfo createUserAdditionalInfo(String businessNumber) {
        return UserAdditionalInfo.builder()
            .businessNumber(businessNumber)
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build();
    }
}