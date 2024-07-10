package com.kkoch.user.api.service.member;

import com.kkoch.user.api.controller.member.response.MemberResponse;
import com.kkoch.user.api.service.member.request.MemberCreateServiceRequest;
import com.kkoch.user.api.service.member.request.MemberPwdModifyServiceRequest;
import com.kkoch.user.api.service.member.request.MemberRemoveServiceRequest;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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
        Member member = getMemberBy(memberKey);

        checkEqualPwd(request.getCurrentPwd(), member.getPwd());

        member.modifyPwd(passwordEncoder.encode(request.getNewPwd()));

        return MemberResponse.of(member);
    }

    public MemberResponse removeMember(String memberKey, MemberRemoveServiceRequest request) {
        Member member = getMemberBy(memberKey);

        checkEqualPwd(request.getPwd(), member.getPwd());

        member.withdrawal();

        return MemberResponse.of(member);
    }

    public Member getUserDetailsByEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("등록되지 않는 사용자입니다."));
    }

    private void checkDuplication(String email, String tel, String businessNumber) {
        checkDuplicationEmail(email);
        checkDuplicationTel(tel);
        checkDuplicationBusinessNumber(businessNumber);
    }

    private void checkDuplicationEmail(String email) {
        boolean isExist = memberRepository.existsByEmail(email);
        if (isExist) {
            throw new AppException("사용중인 이메일입니다.");
        }
    }

    private void checkDuplicationTel(String tel) {
        boolean isExist = memberRepository.existsByTel(tel);
        if (isExist) {
            throw new AppException("사용중인 연락처입니다.");
        }
    }

    private void checkDuplicationBusinessNumber(String businessNumber) {
        boolean isExist = memberRepository.existsByBusinessNumber(businessNumber);
        if (isExist) {
            throw new AppException("사용중인 사업자 번호입니다.");
        }
    }

    private void checkEqualPwd(String currentPwd, String pwd) {
        if (isNotMatch(currentPwd, pwd)) {
            throw new AppException("현재 비밀번호가 일치하지 않습니다.");
        }
    }

    private boolean isNotMatch(String currentPwd, String pwd) {
        return !passwordEncoder.matches(currentPwd, pwd);
    }

    private Member getMemberBy(String memberKey) {
        return memberRepository.findByMemberKey(memberKey)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 사용자입니다."));
    }
}
