package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.IntegrationTestSupport;
import com.ssafy.user_service.api.service.member.request.MemberCreateServiceRequest;
import com.ssafy.user_service.api.service.member.request.MemberPasswordModifyServiceRequest;
import com.ssafy.user_service.api.service.member.response.MemberCreateResponse;
import com.ssafy.user_service.api.service.member.response.MemberPasswordModifyResponse;
import com.ssafy.user_service.common.exception.AppException;
import com.ssafy.user_service.domain.member.*;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
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

    @DisplayName("일반 회원 등록시 입력 받은 이메일을 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void createUserMemberDuplicatedEmail() {
        //given
        Member member = createMember();

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01056785678")
            .businessNumber("1112233333")
            .build();

        //when
        assertThatThrownBy(() -> memberService.createUserMember(request))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 가입된 이메일입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("일반 회원 등록시 입력 받은 연락처를 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void createUserMemberDuplicatedTel() {
        //given
        Member member = createMember();

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@gmail.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .businessNumber("1112233333")
            .build();

        //when
        assertThatThrownBy(() -> memberService.createUserMember(request))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 가입된 연락처입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("일반 회원 등록시 입력 받은 사업자 번호를 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void createUserMemberDuplicatedBusinessNumber() {
        //given
        Member member = createMember();

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@gmail.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01056785678")
            .businessNumber("1231212345")
            .build();

        //when
        assertThatThrownBy(() -> memberService.createUserMember(request))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 가입된 사업자 번호입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("회원 정보를 입력 받아 일반 회원 등록을 한다.")
    @Test
    void createUserMember() {
        //given
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .businessNumber("1231212345")
            .build();

        //when
        MemberCreateResponse response = memberService.createUserMember(request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("email", "ss***@ssafy.com")
            .hasFieldOrPropertyWithValue("name", "김싸피");
    }

    @DisplayName("관리자 회원 등록시 입력 받은 이메일을 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void createAdminMemberDuplicatedEmail() {
        //given
        Member member = createMember();

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김관리")
            .tel("01056785678")
            .build();

        //when
        assertThatThrownBy(() -> memberService.createAdminMember(request))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 가입된 이메일입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("관리자 회원 등록시 입력 받은 연락처를 사용중인 회원이 존재하면 예외가 발생한다.")
    @Test
    void createAdminMemberDuplicatedTel() {
        //given
        Member member = createMember();

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@gmail.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .build();

        //when
        assertThatThrownBy(() -> memberService.createAdminMember(request))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 가입된 연락처입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("회원 정보를 입력 받아 관리자 회원 등록을 한다.")
    @Test
    void createAdminMember() {
        //given
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .build();

        //when
        MemberCreateResponse response = memberService.createAdminMember(request);

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

        Member member = createMember();
        MemberPasswordModifyServiceRequest request = MemberPasswordModifyServiceRequest.builder()
            .currentPassword("ssafy1111!")
            .newPassword("ssafy5678@")
            .build();

        //when
        assertThatThrownBy(() -> memberService.modifyPassword(member.getMemberKey(), currentDateTime, request))
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

        Member member = createMember();
        MemberPasswordModifyServiceRequest request = MemberPasswordModifyServiceRequest.builder()
            .currentPassword("ssafy1234!")
            .newPassword("ssafy5678@")
            .build();

        //when
        MemberPasswordModifyResponse response = memberService.modifyPassword(member.getMemberKey(), currentDateTime, request);

        //then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent();

        boolean matches = passwordEncoder.matches(request.getNewPassword(), findMember.get().getPwd());
        assertThat(matches).isTrue();

        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("passwordModifiedDateTime", currentDateTime);
    }

    private Member createMember() {
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