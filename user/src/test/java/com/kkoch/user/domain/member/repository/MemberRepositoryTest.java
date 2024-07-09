package com.kkoch.user.domain.member.repository;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일 사용여부를 조회한다.")
    @Test
    void existsByEmail1() {
        //given
        Member member = createMember();

        //when
        boolean isExistEmail = memberRepository.existsByEmail(member.getEmail());

        //then
        assertThat(isExistEmail).isTrue();
    }

    @DisplayName("이메일 사용여부를 조회한다.")
    @Test
    void existsByEmail2() {
        //given //when
        boolean isExistEmail = memberRepository.existsByEmail("ssafy@ssafy.com");

        //then
        assertThat(isExistEmail).isFalse();
    }

    @DisplayName("연락처 사용여부를 조회한다.")
    @Test
    void existsByTel1() {
        //given
        Member member = createMember();

        //when
        boolean isExistTel = memberRepository.existsByTel(member.getTel());

        //then
        assertThat(isExistTel).isTrue();
    }

    @DisplayName("연락처 사용여부를 조회한다.")
    @Test
    void existsByTel2() {
        //given //when
        boolean isExistTel = memberRepository.existsByTel("010-1234-1234");

        //then
        assertThat(isExistTel).isFalse();
    }

    @DisplayName("사업자 번호 사용여부를 조회한다.")
    @Test
    void existsByBusinessNumber1() {
        //given
        Member member = createMember();

        //when
        boolean isExistBusinessNumber = memberRepository.existsByBusinessNumber(member.getBusinessNumber());

        //then
        assertThat(isExistBusinessNumber).isTrue();
    }

    @DisplayName("사업자 번호 사용여부를 조회한다.")
    @Test
    void existsByBusinessNumber2() {
        //given //when
        boolean isExistBusinessNumber = memberRepository.existsByBusinessNumber("123-12-12345");

        //then
        assertThat(isExistBusinessNumber).isFalse();
    }

    @DisplayName("이메일로 회원을 조회할 수 있다.")
    @Test
    void findByEmail() {
        //given
        Member member = createMember();

        //when
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());

        //then
        assertThat(findMember).isPresent();
    }

    @DisplayName("회원 고유키로 회원을 조회할 수 있다.")
    @Test
    void findByMemberKey() {
        //given
        Member member = createMember();

        //when
        Optional<Member> findMember = memberRepository.findByMemberKey(member.getMemberKey());

        //then
        assertThat(findMember).isPresent();
    }

    private Member createMember() {
        Member member = Member.builder()
            .isDeleted(false)
            .memberKey(UUID.randomUUID().toString())
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