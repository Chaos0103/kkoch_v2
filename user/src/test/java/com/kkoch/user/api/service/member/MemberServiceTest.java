package com.kkoch.user.api.service.member;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.service.member.response.MemberResponse;
import com.kkoch.user.api.service.member.request.MemberCreateServiceRequest;
import com.kkoch.user.api.service.member.request.MemberPwdModifyServiceRequest;
import com.kkoch.user.api.service.member.request.MemberRemoveServiceRequest;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.Point;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.exception.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("신규 회원 등록시 입력 받은 이메일을 다른 회원이 사용중이라면 예외가 발생한다.")
    @Test
    void createMemberWithDuplicationEmail() {
        //given
        String memberKey = generateMemberKey();
        Member member = createMember(memberKey);

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@ssafy.com")
            .pwd("ssafyc204!")
            .name("김싸피")
            .tel("010-5678-5678")
            .businessNumber("111-11-12345")
            .build();

        //when
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(AppException.class)
            .hasMessage("사용중인 이메일입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("신규 회원 등록시 입력 받은 연락처를 다른 회원이 사용중이라면 예외가 발생한다.")
    @Test
    void createMemberWithDuplicationTel() {
        //given
        String memberKey = generateMemberKey();
        Member member = createMember(memberKey);

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@gmail.com")
            .pwd("ssafyc204!")
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("111-11-12345")
            .build();

        //when
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(AppException.class)
            .hasMessage("사용중인 연락처입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("신규 회원 등록시 입력 받은 사업자 번호를 다른 회원이 사용중이라면 예외가 발생한다.")
    @Test
    void createMemberWithDuplicationBusinessNumber() {
        //given
        String memberKey = generateMemberKey();
        Member member = createMember(memberKey);

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@gmail.com")
            .pwd("ssafyc204!")
            .name("김싸피")
            .tel("010-5678-5678")
            .businessNumber("123-12-12345")
            .build();

        //when
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(AppException.class)
            .hasMessage("사용중인 사업자 번호입니다.");

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("회원 정보를 입력받아 신규 회원을 등록한다.")
    @Test
    void createMember() {
        //given
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("ssafy@ssafy.com")
            .pwd("ssafyc204!")
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .build();

        //when
        MemberResponse response = memberService.createMember(request);

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1)
            .extracting("email", "name", "tel", "businessNumber", "point.value", "isDeleted")
            .containsExactlyInAnyOrder(
                tuple("ssafy@ssafy.com", "김싸피", "010-1234-1234", "123-12-12345", 0L, false)
            );
    }

    @DisplayName("비밀번호 변경시 입력 받은 현재 비밀번호와 등록된 비밀번호가 일치하지 않는다면 예외가 발생한다.")
    @Test
    void modifyPwdWithNotEqualCurrentPwd() {
        //given
        String memberKey = generateMemberKey();
        Member member = createMember(memberKey);

        MemberPwdModifyServiceRequest request = MemberPwdModifyServiceRequest.builder()
            .currentPwd("password1!")
            .newPwd("newPwd1!")
            .build();

        //when
        assertThatThrownBy(() -> memberService.modifyPwd(member.getMemberKey(), request))
            .isInstanceOf(AppException.class)
            .hasMessage("현재 비밀번호가 일치하지 않습니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent();

        boolean isMatch = passwordEncoder.matches("ssafy1234!", findMember.get().getPwd());
        assertThat(isMatch).isTrue();
    }

    @DisplayName("회원은 비밀번호를 변경할 수 있다.")
    @Test
    void modifyPwd() {
        //given
        String memberKey = generateMemberKey();
        Member member = createMember(memberKey);

        MemberPwdModifyServiceRequest request = MemberPwdModifyServiceRequest.builder()
            .currentPwd("ssafy1234!")
            .newPwd("newPwd1234!")
            .build();

        //when
        MemberResponse response = memberService.modifyPwd(member.getMemberKey(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("email", "ssafy@ssafy.com")
            .hasFieldOrPropertyWithValue("name", "김싸피")
            .hasFieldOrPropertyWithValue("memberKey", memberKey);

        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent();

        boolean result = passwordEncoder.matches(request.getNewPwd(), findMember.get().getPwd());
        assertThat(result).isTrue();
    }

    @DisplayName("회원 탈퇴시 현재 비밀번호가 일치하지 않는다면 예외가 발생한다.")
    @Test
    void removeMemberWithNotEqualCurrentPwd() {
        //given
        String memberKey = generateMemberKey();
        Member member = createMember(memberKey);
        MemberRemoveServiceRequest request = MemberRemoveServiceRequest.builder()
            .pwd("password1!")
            .build();

        //when
        assertThatThrownBy(() -> memberService.removeMember(member.getMemberKey(), request))
            .isInstanceOf(AppException.class)
            .hasMessage("현재 비밀번호가 일치하지 않습니다.");

        //then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", false);
    }

    @DisplayName("회원은 회원 탈퇴를 할 수 있다.")
    @Test
    void removeMember() {
        //given
        String memberKey = generateMemberKey();
        Member member = createMember(memberKey);
        MemberRemoveServiceRequest request = MemberRemoveServiceRequest.builder()
            .pwd("ssafy1234!")
            .build();

        //when
        MemberResponse response = memberService.removeMember(member.getMemberKey(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("email", "ssafy@ssafy.com")
            .hasFieldOrPropertyWithValue("name", "김싸피")
            .hasFieldOrPropertyWithValue("memberKey", memberKey);

        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", true);
    }

    private Member createMember(String memberKey) {
        Member member = Member.builder()
            .isDeleted(false)
            .memberKey(memberKey)
            .email("ssafy@ssafy.com")
            .pwd(passwordEncoder.encode("ssafy1234!"))
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .point(Point.builder()
                .value(0)
                .build())
            .build();
        return memberRepository.save(member);
    }
}