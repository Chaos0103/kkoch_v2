package com.kkoch.user.api.service.member;

import com.kkoch.user.api.controller.member.response.MemberResponse;
import com.kkoch.user.api.service.member.dto.MemberCreateServiceRequest;
import com.kkoch.user.api.service.member.request.MemberPwdModifyServiceRequest;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponse createMember(MemberCreateServiceRequest request) {
        checkDuplication(request.getEmail(), request.getTel(), request.getBusinessNumber());

        Member member = request.toEntity(passwordEncoder.encode(request.getPwd()));
        Member savedMember = memberRepository.save(member);

        return MemberResponse.of(savedMember);
    }

    public MemberResponse modifyPwd(String memberKey, MemberPwdModifyServiceRequest request) {
        Member member = getMember(memberKey);

        matchCurrentPwd(request.getCurrentPwd(), member);

        member.changePwd(passwordEncoder.encode(request.getNextPwd()));

        return MemberResponse.of(member);
    }

    public MemberResponse withdrawal(String memberKey, String pwd) {
        Member member = getMember(memberKey);

        matchCurrentPwd(pwd, member);

        member.withdrawal();

        return MemberResponse.of(member);
    }

    public Member getUserDetailsByEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);

        if (findMember.isEmpty()) {
            throw new UsernameNotFoundException("등록되지 않는 사용자입니다.");
        }

        return findMember.get();
    }

    private void checkDuplication(String email, String tel, String businessNumber) {
        boolean isExistEmail = memberRepository.existsByEmail(email);
        if (isExistEmail) {
            throw new AppException("사용중인 이메일입니다.");
        }

        boolean isExistTel = memberRepository.existsByTel(tel);
        if (isExistTel) {
            throw new AppException("사용중인 연락처입니다.");
        }

        boolean isExistBusinessNumber = memberRepository.existsByBusinessNumber(businessNumber);
        if (isExistBusinessNumber) {
            throw new AppException("사용중인 사업자 번호입니다.");
        }
    }

    private Member getMember(String memberKey) {
        return memberRepository.findByMemberKey(memberKey)
            .orElseThrow(NoSuchElementException::new);
    }

    private void matchCurrentPwd(String currentPwd, Member member) {
        boolean matches = passwordEncoder.matches(currentPwd, member.getPwd());
        if (!matches) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
    }
}
