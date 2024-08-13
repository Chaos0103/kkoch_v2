package com.ssafy.user_service.domain.member.repository;

import com.ssafy.user_service.IntegrationTestSupport;
import com.ssafy.user_service.domain.member.*;
import com.ssafy.user_service.domain.member.repository.response.MemberInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 고유키로 회원 정보를 조회한다.")
    @Test
    void findByMemberKey() {
        //given
        Member member = createMember();

        //when
        Optional<MemberInfoResponse> content = memberQueryRepository.findByMemberKey(member.getMemberKey());

        //then
        assertThat(content).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("email", "ssafy@ssafy.com")
            .hasFieldOrPropertyWithValue("name", "김싸피")
            .hasFieldOrPropertyWithValue("tel", "01012341234")
            .hasFieldOrPropertyWithValue("businessNumber", "1231212345");
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