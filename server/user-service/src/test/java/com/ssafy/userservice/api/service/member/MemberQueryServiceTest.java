package com.ssafy.userservice.api.service.member;

import com.ssafy.common.global.exception.MemberException;
import com.ssafy.userservice.api.service.member.response.MemberDisplayInfoResponse;
import com.ssafy.userservice.api.service.member.response.MemberIdInnerClientResponse;
import com.ssafy.userservice.domain.member.Member;
import com.ssafy.userservice.domain.member.enums.Role;
import com.ssafy.userservice.domain.member.repository.MemberRepository;
import com.ssafy.userservice.domain.member.vo.BankAccount;
import com.ssafy.userservice.domain.member.vo.MemberSpecificInfo;
import com.ssafy.userservice.domain.member.vo.UserAdditionalInfo;
import common.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    MemberQueryService memberQueryService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder encoder;

    @DisplayName("회원 정보 조회 시 입력 받은 회원 고유키와 일치하는 회원을 찾지 못한 경우 예외가 발생한다.")
    @Test
    void searchMemberInfoWhenNotFound() {
        //given
        String memberKey = "invalidMemberKey";

        //when //then
        assertThatThrownBy(() -> memberQueryService.searchMemberInfo(memberKey))
            .isInstanceOf(MemberException.class)
            .hasMessage("회원을 찾을 수 없습니다.");
    }

    @DisplayName("입력 받은 회원 고유키와 일치하는 회원의 정보를 일부 마스킹하여 반환한다.")
    @Test
    void searchMemberInfo() {
        //given
        String memberKey = "validMemberKey";
        createMember(memberKey);

        //when
        MemberDisplayInfoResponse memberDisplayInfoResponse = memberQueryService.searchMemberInfo(memberKey);

        //then
        assertThat(memberDisplayInfoResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.getEmail()).isEqualTo("ss***@gmail.com");
                assertThat(response.getName()).isEqualTo("김싸피");
                assertThat(response.getTel()).isEqualTo("010****1234");
            });
    }

    @DisplayName("회원 기본키 조회 시 입력 받은 회원 고유키와 일치하는 회원을 찾지 못한 경우 예외가 발생한다.")
    @Test
    void searchMemberIdWhenNotFound() {
        //given
        String memberKey = "invalidMemberKey";

        //when //then
        assertThatThrownBy(() -> memberQueryService.searchMemberId(memberKey))
            .isInstanceOf(MemberException.class)
            .hasMessage("회원을 찾을 수 없습니다.");
    }

    @DisplayName("입력 받은 회원 고유키와 일치하는 회원의 기본키를 반환한다.")
    @Test
    void searchMemberId() {
        //given
        String memberKey = "validMemberKey";
        Member createdMember = createMember(memberKey);

        //when
        MemberIdInnerClientResponse memberIdInnerClientResponse = memberQueryService.searchMemberId(memberKey);

        //then
        assertThat(memberIdInnerClientResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.getMemberId()).isEqualTo(createdMember.getId());
            });
    }

    private Member createMember(String memberKey) {
        Member member = Member.builder()
            .isDeleted(false)
            .specificInfo(createSpecificInfo(memberKey))
            .email("ssafy@gmail.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .userAdditionalInfo(createDefaultUserAdditionalInfo())
            .encoder(encoder)
            .build();
        return memberRepository.save(member);
    }

    private MemberSpecificInfo createSpecificInfo(String memberKey) {
        return MemberSpecificInfo.builder()
            .memberKey(memberKey)
            .role(Role.USER)
            .build();
    }

    private UserAdditionalInfo createDefaultUserAdditionalInfo() {
        return UserAdditionalInfo.builder()
            .businessNumber("1231212345")
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