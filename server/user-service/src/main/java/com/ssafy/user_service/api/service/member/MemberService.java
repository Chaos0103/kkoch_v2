package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.api.service.member.request.MemberCreateServiceRequest;
import com.ssafy.user_service.api.service.member.request.MemberPasswordModifyServiceRequest;
import com.ssafy.user_service.api.service.member.request.MemberTelModifyServiceRequest;
import com.ssafy.user_service.api.service.member.response.MemberCreateResponse;
import com.ssafy.user_service.api.service.member.response.MemberPasswordModifyResponse;
import com.ssafy.user_service.api.service.member.response.MemberTelModifyResponse;
import com.ssafy.user_service.common.exception.AppException;
import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.ssafy.user_service.api.service.member.MemberValidate.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberCreateResponse createUserMember(MemberCreateServiceRequest request) {
        checkBusinessNumber(request.getBusinessNumber());

        return createMember(request);
    }

    public MemberCreateResponse createAdminMember(MemberCreateServiceRequest request) {
        return createMember(request);
    }

    public MemberPasswordModifyResponse modifyPassword(String memberKey, LocalDateTime currentDateTime, MemberPasswordModifyServiceRequest request) {
        Member member = findMemberBy(memberKey);

        checkMatchesPassword(member, request.getCurrentPassword());

        checkPassword(request.getNewPassword());

        modifyPassword(member, request.getNewPassword());

        return MemberPasswordModifyResponse.of(currentDateTime);
    }

    public MemberTelModifyResponse modifyTel(String memberKey, LocalDateTime currentDateTime, MemberTelModifyServiceRequest request) {
        return null;
    }

    private void modifyPassword(Member member, String newPassword) {
        String encodedPassword = generateEncodedPassword(newPassword);
        member.modifyPassword(encodedPassword);
    }

    private void checkMatchesPassword(Member member, String currentPassword) {
        if (member.isNotMatchesPwd(passwordEncoder, currentPassword)) {
            throw new AppException("비밀번호가 일치하지 않습니다.");
        }
    }

    private MemberCreateResponse createMember(MemberCreateServiceRequest request) {
        checkEmail(request.getEmail());
        checkPassword(request.getPassword());
        checkName(request.getName());
        checkTel(request.getTel());

        Member member = request.toEntity(generateEncodedPassword(request.getPassword()));
        Member savedMember = memberRepository.save(member);

        return MemberCreateResponse.of(savedMember);
    }

    private void checkEmail(String email) {
        checkDuplicateEmail(email);
        validateEmail(email);
    }

    private void checkDuplicateEmail(String email) {
        boolean isExistEmail = memberRepository.existsByEmail(email);
        if (isExistEmail) {
            throw new AppException("이미 가입된 이메일입니다.");
        }
    }

    private static void checkPassword(String password) {
        validatePassword(password);
    }

    private static void checkName(String name) {
        validateName(name);
    }

    private void checkTel(String tel) {
        checkDuplicateTel(tel);
        validateTel(tel);
    }

    private void checkDuplicateTel(String tel) {
        boolean isExistTel = memberRepository.existsByTel(tel);
        if (isExistTel) {
            throw new AppException("이미 가입된 연락처입니다.");
        }
    }

    private void checkBusinessNumber(String businessNumber) {
        checkDuplicateBusinessNumber(businessNumber);
        validateBusinessNumber(businessNumber);
    }

    private void checkDuplicateBusinessNumber(String businessNumber) {
        boolean isExistBusinessNumber = memberRepository.existsByUserAdditionalInfoBusinessNumber(businessNumber);
        if (isExistBusinessNumber) {
            throw new AppException("이미 가입된 사업자 번호입니다.");
        }
    }

    private String generateEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private Member findMemberBy(String memberKey) {
        return memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 회원입니다."));
    }
}
