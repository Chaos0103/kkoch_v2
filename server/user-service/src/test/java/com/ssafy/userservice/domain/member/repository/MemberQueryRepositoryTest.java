package com.ssafy.userservice.domain.member.repository;

import com.ssafy.userservice.domain.member.Member;
import com.ssafy.userservice.domain.member.enums.Role;
import com.ssafy.userservice.domain.member.repository.response.MemberDisplayInfoDto;
import com.ssafy.userservice.domain.member.vo.*;
import common.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    MemberQueryRepository memberQueryRepository;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("회원 정보 조회 시 입력 받은 회원 고유키와 일치하는 회원이 삭제된 경우 빈 값을 반환한다.")
    @Test
    void findMemberDisplayInfoWhenDeletedMember() {
        //given
        String memberKey = "deletedMemberKey";
        createMember(memberKey, true);

        //when
        Optional<MemberDisplayInfoDto> content = memberQueryRepository.findMemberDisplayInfoByMemberKey(memberKey);

        //then
        assertThat(content).isEmpty();
    }

    @DisplayName("회원 정보 조회 시 입력 받은 회원 고유키와 일치하는 회원이 없을 경우 빈 값을 반환한다.")
    @Test
    void findMemberDisplayInfoWhenInvalidMemberKey() {
        //given
        String memberKey = "invalidMemberKey";

        //when
        Optional<MemberDisplayInfoDto> content = memberQueryRepository.findMemberDisplayInfoByMemberKey(memberKey);

        //then
        assertThat(content).isEmpty();
    }

    @DisplayName("입력 받은 회원 고유키와 일치하는 회원의 정보를 조회한다.")
    @Test
    void findMemberDisplayInfoWhenValidMemberKey() {
        //given
        String memberKey = "validMemberKey";
        createMember(memberKey, false);

        //when
        Optional<MemberDisplayInfoDto> content = memberQueryRepository.findMemberDisplayInfoByMemberKey(memberKey);

        //then
        assertThat(content).isPresent()
            .hasValueSatisfying(memberDisplayInfoDto -> {
                assertThat(memberDisplayInfoDto)
                    .extracting("email", "name", "tel")
                    .containsExactly("ssafy@gmail.com", "김싸피", "01012341234");
            });
    }

    @DisplayName("회원 기본키 조회 시 입력 받은 회원 고유키와 일치하는 회원이 삭제된 경우 빈 값을 반환한다.")
    @Test
    void findMemberIdWhenDeletedMember() {
        //given
        String memberKey = "deletedMemberKey";
        createMember(memberKey, true);

        //when
        Optional<Long> findMemberId = memberQueryRepository.findMemberIdByMemberKey(memberKey);

        //then
        assertThat(findMemberId).isEmpty();
    }

    @DisplayName("회원 기본키 조회 시 입력 받은 회원 고유키와 일치하는 회원이 없을 경우 빈 값을 반환한다.")
    @Test
    void findMemberIdWhenInvalidMemberKey() {
        //given
        String memberKey = "invalidMemberKey";

        //when
        Optional<Long> findMemberId = memberQueryRepository.findMemberIdByMemberKey(memberKey);

        //then
        assertThat(findMemberId).isEmpty();
    }

    @DisplayName("입력 받은 회원 고유키와 일치하는 회원의 기본키를 조회한다.")
    @Test
    void findMemberIdWhenValidMemberKey() {
        //given
        String memberKey = "validMemberKey";
        Member member = createMember(memberKey, false);

        //when
        Optional<Long> findMemberId = memberQueryRepository.findMemberIdByMemberKey(memberKey);

        //then
        assertThat(findMemberId)
            .isPresent()
            .hasValue(member.getId());
    }

    private Member createMember(String memberKey, boolean isDeleted) {
        Member member = Member.builder()
            .isDeleted(isDeleted)
            .specificInfo(createSpecificInfo(memberKey))
            .email(Email.of("ssafy@gmail.com"))
            .password(Password.of("ssafy1234!"))
            .name(Name.of("김싸피"))
            .tel(Tel.of("01012341234"))
            .userAdditionalInfo(createDefaultUserAdditionalInfo())
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
            .businessNumber(BusinessNumber.of("1231212345"))
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