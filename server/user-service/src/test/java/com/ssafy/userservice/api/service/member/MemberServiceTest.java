package com.ssafy.userservice.api.service.member;

import com.ssafy.common.global.exception.MemberException;
import com.ssafy.userservice.api.service.member.request.*;
import com.ssafy.userservice.api.service.member.response.*;
import com.ssafy.userservice.domain.member.Member;
import com.ssafy.userservice.domain.member.enums.Role;
import com.ssafy.userservice.domain.member.repository.MemberRepository;
import com.ssafy.userservice.domain.member.vo.BankAccount;
import com.ssafy.userservice.domain.member.vo.BusinessNumber;
import com.ssafy.userservice.domain.member.vo.MemberSpecificInfo;
import com.ssafy.userservice.domain.member.vo.UserAdditionalInfo;
import common.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder encoder;

    @DisplayName("회원 등록 시 입력 받은 이메일을 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void createMemberDuplicatedEmail() {
        //given
        String email = "ssafy@gmail.com";
        createMember("validMemberKey", Role.USER, email, "ssafy1234!", "01012341234", null);

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email(email)
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01056785678")
            .role(Role.USER)
            .build();

        //when
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(MemberException.class)
            .hasMessage("이미 사용중인 이메일입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("회원 등록 시 입력 받은 연락처를 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void createMemberDuplicatedTel() {
        //given
        String tel = "01012341234";
        createMember("validMemberKey", Role.USER, "ssafy@gmail.com", "ssafy1234!", tel, null);

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("other@gmail.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel(tel)
            .role(Role.USER)
            .build();

        //when
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(MemberException.class)
            .hasMessage("이미 사용중인 연락처입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("회원 정보를 입력 받아 회원 등록을 한다.")
    @Test
    void createMember() {
        //given
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@gmail.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .role(Role.USER)
            .build();

        //when
        MemberCreateResponse response = memberService.createMember(request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("email", "ss***@gmail.com")
            .hasFieldOrPropertyWithValue("name", "김싸피");
    }

    @DisplayName("비밀번호 수정 시 입력 받은 회원 고유키와 일치하는 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void modifyPasswordWhenNotFoundMember() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "invalidMemberKey";
        MemberPasswordModifyServiceRequest request = MemberPasswordModifyServiceRequest.builder()
            .currentPassword("ssafy1234!")
            .newPassword("ssafy5678@")
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.modifyPassword(memberKey, currentDateTime, request))
            .isInstanceOf(MemberException.class)
            .hasMessage("회원을 찾을 수 없습니다.");
    }

    @DisplayName("비밀번호 수정 시 입력 받은 현재 비밀번호가 수정할 회원의 비밀번호와 일치하지 않으면 예외가 발생한다.")
    @Test
    void modifyPasswordWhenCurrentPasswordDoseNotMatches() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();

        String memberKey = "validMemberKey";
        String currentPassword = "ssafy1234!";
        Member createdMember = createMember(memberKey, Role.USER, "ssafy@gmail.com", currentPassword, "01012341234", null);
        MemberPasswordModifyServiceRequest request = MemberPasswordModifyServiceRequest.builder()
            .currentPassword("pwd1357@")
            .newPassword("ssafy5678@")
            .build();

        //when
        assertThatThrownBy(() -> memberService.modifyPassword(memberKey, currentDateTime, request))
            .isInstanceOf(MemberException.class)
            .hasMessage("비밀번호가 일치하지 않습니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(createdMember.getId());
        assertThat(findMember)
            .isPresent()
            .hasValueSatisfying(member ->
                assertThat(encoder.matches(currentPassword, member.getPassword().getPassword())).isTrue()
            );
    }

    @DisplayName("입력 받은 현재 비밀번호가 수정할 회원의 비밀번호와 일치하면 신규 비밀번호로 수정한다.")
    @Test
    void modifyPassword() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();

        String memberKey = "validMemberKey";
        String currentPassword = "ssafy1234!";
        String newPassword = "ssafy5678@";
        Member createdMember = createMember(memberKey, Role.USER, "ssafy@gmail.com", currentPassword, "01012341234", null);
        MemberPasswordModifyServiceRequest request = MemberPasswordModifyServiceRequest.builder()
            .currentPassword(currentPassword)
            .newPassword(newPassword)
            .build();

        //when
        MemberPasswordModifyResponse memberPasswordModifyResponse = memberService.modifyPassword(memberKey, currentDateTime, request);

        //then
        assertThat(memberPasswordModifyResponse)
            .isNotNull()
            .satisfies(response ->
                assertThat(response.getPasswordModifiedDateTime()).isEqualTo(currentDateTime)
            );

        Optional<Member> findMember = memberRepository.findById(createdMember.getId());
        assertThat(findMember)
            .isPresent()
            .hasValueSatisfying(member ->
                assertThat(encoder.matches(newPassword, member.getPassword().getPassword())).isTrue()
            );
    }

    @DisplayName("연락처 수정 시 입력 받은 회원 고유키와 일치하는 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void modifyTelWhenNotFoundMember() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "invalidMemberKey";
        MemberTelModifyServiceRequest request = MemberTelModifyServiceRequest.builder()
            .tel("01056785678")
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.modifyTel(memberKey, currentDateTime, request))
            .isInstanceOf(MemberException.class)
            .hasMessage("회원을 찾을 수 없습니다.");
    }

    @DisplayName("연락처 수정 시 입력 받은 연락처를 현재 본인이 사용중이라면 예외가 발생한다.")
    @Test
    void modifyTelWhenCurrentTelDoesMatch() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "validMemberKey";
        String tel = "01012341234";
        Member createdMember = createMember(memberKey, Role.USER, "ssafy@gmail.com", "ssafy1234!", tel, null);
        MemberTelModifyServiceRequest request = MemberTelModifyServiceRequest.builder()
            .tel(tel)
            .build();

        //when
        assertThatThrownBy(() -> memberService.modifyTel(memberKey, currentDateTime, request))
            .isInstanceOf(MemberException.class)
            .hasMessage("현재 사용중인 연락처입니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(createdMember.getId());
        assertThat(findMember)
            .isPresent()
            .hasValueSatisfying(member ->
                assertThat(member.getTel()).isEqualTo(tel)
            );
    }

    @DisplayName("회원 수정 시 입력 받은 연락처를 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void modifyTelWhenTelIsDuplicate() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "validMemberKey";
        String tel = "01056785678";
        Member createdMember = createMember(memberKey, Role.USER, "ssafy@gmail.com", "ssafy1234!", "01012341234", null);
        MemberTelModifyServiceRequest request = MemberTelModifyServiceRequest.builder()
            .tel(tel)
            .build();

        createMember("otherMemberKey", Role.USER, "other@gmail.com", "other1111@", tel, null);

        //when
        assertThatThrownBy(() -> memberService.modifyTel(memberKey, currentDateTime, request))
            .isInstanceOf(MemberException.class)
            .hasMessage("이미 사용중인 연락처입니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(createdMember.getId());
        assertThat(findMember)
            .isPresent()
            .hasValueSatisfying(member ->
                assertThat(member.getTel()).isEqualTo("01012341234")
            );
    }

    @DisplayName("입력 받은 연락처를 사용중인 다른 회원이 없다면 회원의 연락처를 수정한다.")
    @Test
    void modifyTel() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "validMemberKey";
        String tel = "01056785678";
        Member createdMember = createMember(memberKey, Role.USER, "ssafy@gmail.com", "ssafy1234!", "01012341234", null);
        MemberTelModifyServiceRequest request = MemberTelModifyServiceRequest.builder()
            .tel(tel)
            .build();

        //when
        MemberTelModifyResponse memberTelModifyResponse = memberService.modifyTel(memberKey, currentDateTime, request);

        //then
        assertThat(memberTelModifyResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.getModifiedTel()).isEqualTo(tel);
                assertThat(response.getTelModifiedDateTime()).isEqualTo(currentDateTime);
            });

        Optional<Member> findMember = memberRepository.findById(createdMember.getId());
        assertThat(findMember)
            .isPresent()
            .hasValueSatisfying(member ->
                assertThat(member.getTel()).isEqualTo(tel)
            );
    }

    @DisplayName("사업자 번호 등록 시 입력 받은 회원 고유키와 일치하는 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void registerBusinessNumberWhenNotFoundMember() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "invalidMemberKey";
        RegisterBusinessNumberServiceRequest request = RegisterBusinessNumberServiceRequest.builder()
            .businessNumber("1231212345")
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.registerBusinessNumber(memberKey, currentDateTime, request))
            .isInstanceOf(MemberException.class)
            .hasMessage("회원을 찾을 수 없습니다.");
    }

    @DisplayName("사업자 번호를 등록할 회원이 이미 사업자 번호를 가지고 있다면 예외가 발생한다.")
    @Test
    void registerBusinessNumberWhenHasBusinessNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "validMemberKey";
        createMember(memberKey, Role.BUSINESS, "ssafy@gmail.com", "ssafy1234!", "01012341234", createUserAdditionalInfo("1231212345"));
        RegisterBusinessNumberServiceRequest request = RegisterBusinessNumberServiceRequest.builder()
            .businessNumber("1231212345")
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.registerBusinessNumber(memberKey, currentDateTime, request))
            .isInstanceOf(MemberException.class)
            .hasMessage("사업자 번호가 등록된 회원입니다.");
    }

    @DisplayName("사업자 번호 등록 시 입력 받은 사업자 번호를 사용하는 다른 회원이 존재하면 예외가 발생한다.")
    @Test
    void registerBusinessNumberWhenBusinessNumberIsDuplicate() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "validMemberKey";
        Member createdMember = createMember(memberKey, Role.USER, "ssafy@gmail.com", "ssafy1234!", "01012341234", null);
        RegisterBusinessNumberServiceRequest request = RegisterBusinessNumberServiceRequest.builder()
            .businessNumber("1231212345")
            .build();

        createMember("otherMemberKey", Role.BUSINESS, "other@gmail.com", "other1234@", "01056785678", createUserAdditionalInfo("1231212345"));

        //when
        assertThatThrownBy(() -> memberService.registerBusinessNumber(memberKey, currentDateTime, request))
            .isInstanceOf(MemberException.class)
            .hasMessage("이미 사용중인 사업자 번호입니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(createdMember.getId());
        assertThat(findMember)
            .isPresent()
            .hasValueSatisfying(member -> {
                assertThat(member.getSpecificInfo().getRole()).isEqualTo(Role.USER);
                assertThat(member.getUserAdditionalInfo()).isNull();
            });
    }

    @DisplayName("사업자 번호를 미등록한 회원의 사업자 번호를 등록한다.")
    @Test
    void registerBusinessNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "validMemberKey";
        Member createdMember = createMember(memberKey, Role.USER, "ssafy@gmail.com", "ssafy1234!", "01012341234", null);

        String businessNumber = "1231212345";
        RegisterBusinessNumberServiceRequest request = RegisterBusinessNumberServiceRequest.builder()
            .businessNumber(businessNumber)
            .build();

        //when
        RegisterBusinessNumberResponse registerBusinessNumberResponse = memberService.registerBusinessNumber(memberKey, currentDateTime, request);

        //then
        assertThat(registerBusinessNumberResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.getBusinessNumber()).isEqualTo(businessNumber);
                assertThat(response.getRegisteredDateTime()).isEqualTo(currentDateTime);
            });

        Optional<Member> findMember = memberRepository.findById(createdMember.getId());
        assertThat(findMember)
            .isPresent()
            .hasValueSatisfying(member -> {
                assertThat(member.getSpecificInfo().getRole()).isEqualTo(Role.BUSINESS);
                assertThat(member.getUserAdditionalInfo()).isNotNull();
                assertThat(member.getUserAdditionalInfo().getBusinessNumber()).isEqualTo(BusinessNumber.of(businessNumber));
            });
    }

    @DisplayName("은행 계좌 수정 시 입력 받은 회원 고유키와 일치하는 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void modifyBankAccountWhenNotFoundMember() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "invalidMemberKey";
        MemberBankAccountModifyServiceRequest request = MemberBankAccountModifyServiceRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.modifyBankAccount(memberKey, currentDateTime, request))
            .isInstanceOf(MemberException.class)
            .hasMessage("회원을 찾을 수 없습니다.");
    }

    @DisplayName("은행 계좌를 수정할 회원이 사업자 회원이 아니라면 예외가 발생한다.")
    @Test
    void modifyBankAccountWhenRoleDoesNotBusiness() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "validMemberKey";
        Member createdMember = createMember(memberKey, Role.USER, "ssafy@gmail.com", "ssafy1234!", "01012341234", null);

        MemberBankAccountModifyServiceRequest request = MemberBankAccountModifyServiceRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        //when
        assertThatThrownBy(() -> memberService.modifyBankAccount(memberKey, currentDateTime, request))
            .isInstanceOf(MemberException.class)
            .hasMessage("사업자 회원이 아닙니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(createdMember.getId());
        assertThat(findMember)
            .isPresent()
            .hasValueSatisfying(member ->
                assertThat(member.getUserAdditionalInfo()).isNull()
            );
    }

    @DisplayName("사업자 회원은 은행 계좌를 수정할 수 있다.")
    @Test
    void modifyBankAccount() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "validMemberKey";
        Member createdMember = createMember(memberKey, Role.BUSINESS, "ssafy@naver.com", "ssafy1234!", "01012341234", createUserAdditionalInfo("1231212345"));
        MemberBankAccountModifyServiceRequest request = MemberBankAccountModifyServiceRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        //when
        MemberBankAccountModifyResponse memberBankAccountModifyResponse = memberService.modifyBankAccount(memberKey, currentDateTime, request);

        //then
        assertThat(memberBankAccountModifyResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.getBankCode()).isEqualTo("088");
                assertThat(response.getAccountNumber()).isEqualTo("123***123456");
                assertThat(response.getBankAccountModifiedDateTime()).isEqualTo(currentDateTime);
            });

        Optional<Member> findMember = memberRepository.findById(createdMember.getId());
        assertThat(findMember)
            .isPresent()
            .hasValueSatisfying(member -> {
                assertThat(member.getBankAccount()).isNotNull();
                assertThat(member.getBankAccount().getBankCode()).isEqualTo("088");
                assertThat(member.getBankAccount().getAccountNumber().getAccountNumber()).isEqualTo("123123123456");
            });
    }

    @DisplayName("회원 탈퇴 시 입력 받은 이메일을 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void removeMemberWhenNotFoundMember() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "validMemberKey";
        String password = "ssafy1234!";

        //when //then
        assertThatThrownBy(() -> memberService.removeMember(memberKey, password, currentDateTime))
            .isInstanceOf(MemberException.class)
            .hasMessage("회원을 찾을 수 없습니다.");
    }

    @DisplayName("회원 탈퇴 시 입력 받은 비밀번호가 회원의 비밀번호와 일치하지 않으면 예외가 발생한다.")
    @Test
    void removeMemberWhenPasswordDoesNotMatch() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "validMemberKey";
        String password = "other5678@";
        Member createdMember = createMember(memberKey, Role.USER, "ssafy@gmail.com", "ssafy1234!", "01012341234", null);

        //when
        assertThatThrownBy(() -> memberService.removeMember(memberKey, password, currentDateTime))
            .isInstanceOf(MemberException.class)
            .hasMessage("비밀번호가 일치하지 않습니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(createdMember.getId());
        assertThat(findMember)
            .isPresent()
            .hasValueSatisfying(member ->
                assertThat(member.getIsDeleted()).isFalse()
            );
    }

    @DisplayName("회원의 비밀번호가 일치하면 회원 탈퇴를 한다.")
    @Test
    void removeMember() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        String memberKey = "validMemberKey";
        String password = "ssafy1234!";
        Member createdMember = createMember(memberKey, Role.USER, "ssafy@gmail.com", password, "01012341234", null);

        //when
        MemberRemoveResponse memberRemoveResponse = memberService.removeMember(memberKey, password, currentDateTime);

        //then
        assertThat(memberRemoveResponse)
            .isNotNull()
            .satisfies(response ->
                assertThat(response.getWithdrawnDateTime()).isEqualTo(currentDateTime)
            );

        Optional<Member> findMember = memberRepository.findById(createdMember.getId());
        assertThat(findMember)
            .isPresent()
            .hasValueSatisfying(member ->
                assertThat(member.getIsDeleted()).isTrue()
            );
    }

    private Member createMember(String memberKey, Role role, String email, String password, String tel, UserAdditionalInfo userAdditionalInfo) {
        Member member = Member.builder()
            .isDeleted(false)
            .specificInfo(createSpecificInfo(memberKey, role))
            .email(email)
            .password(password)
            .name("김싸피")
            .tel(tel)
            .userAdditionalInfo(userAdditionalInfo)
            .encoder(encoder)
            .build();
        return memberRepository.save(member);
    }

    private MemberSpecificInfo createSpecificInfo(String memberKey, Role role) {
        return MemberSpecificInfo.builder()
            .memberKey(memberKey)
            .role(role)
            .build();
    }

    private UserAdditionalInfo createUserAdditionalInfo(String businessNumber) {
        return UserAdditionalInfo.builder()
            .businessNumber(businessNumber)
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