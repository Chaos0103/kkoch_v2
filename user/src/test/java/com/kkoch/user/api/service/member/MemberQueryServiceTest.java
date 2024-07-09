package com.kkoch.user.api.service.member;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.domain.member.Point;
import com.kkoch.user.domain.member.repository.response.MemberInfoResponse;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MemberQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberQueryService memberQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원은 본인의 정보를 조회할 수 있다.")
    @Test
    void getMemberInfoBy() {
        //given
        Member member = createMember();

        //when
        MemberInfoResponse memberInfo = memberQueryService.getMemberInfoBy(member.getMemberKey());

        //then
        assertThat(memberInfo).isNotNull();
    }

    @DisplayName("이메일 존재 여부를 확인할 수 있다.")
    @Test
    void isUsedEmailBy() {
        //given
        Member member = createMember();

        //when
        boolean result = memberQueryService.isUsedEmailBy(member.getEmail());

        //then
        assertThat(result).isTrue();
    }

    private Member createMember() {
        Member member = Member.builder()
            .email("ssafy@ssafy.com")
            .pwd(passwordEncoder.encode("ssafy1234!"))
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .point(
                Point.builder()
                    .value(0)
                    .build()
            )
            .isDeleted(false)
            .memberKey(UUID.randomUUID().toString())
            .build();
        return memberRepository.save(member);
    }
}