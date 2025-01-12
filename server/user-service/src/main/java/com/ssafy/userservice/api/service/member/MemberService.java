package com.ssafy.userservice.api.service.member;

import com.ssafy.common.global.exception.MemberException;
import com.ssafy.userservice.api.service.member.request.*;
import com.ssafy.userservice.api.service.member.response.*;
import com.ssafy.userservice.domain.member.Member;
import com.ssafy.userservice.domain.member.repository.MemberRepository;
import com.ssafy.userservice.domain.member.vo.BankAccount;
import com.ssafy.userservice.domain.member.vo.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.ssafy.common.global.exception.code.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private static final String USERNAME_NOT_FOUND = "계정을 확인해주세요.";

    private final MemberRepository memberRepository;

    public MemberCreateResponse createMember(MemberCreateServiceRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            log.warn("회원 가입 이메일 중복 [email = {}]", request.getEmail());
            throw MemberException.of(DUPLICATE_EMAIL);
        }

        if (memberRepository.existsByTel(request.getTel())) {
            log.warn("회원 가입 연락처 중복 [tel = {}]", request.getTel());
            throw MemberException.of(DUPLICATE_TEL);
        }

        Member member = request.toEntity();
        Member savedMember = memberRepository.save(member);

        log.info("신규 회원 등록 [memberKey = {}, role = {}, email = {}, name = {}]", savedMember.getMemberKey(), savedMember.getSpecificInfo().getRole(), savedMember.getEmail().getEmail(), savedMember.getName());
        return MemberCreateResponse.of(savedMember);
    }

    public MemberPasswordModifyResponse modifyPassword(String memberKey, LocalDateTime currentDateTime, MemberPasswordModifyServiceRequest request) {
        Member member = memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        if (member.isNotMatchesPwd(request.getCurrentPassword())) {
            log.debug("회원 비밀번호 수정: 비밀번호 불일치 [password = {}]", request.getCurrentPassword());
            throw MemberException.of(NOT_MATCH_PASSWORD);
        }

        member.modifyPassword(request.getNewPassword());

        log.info("회원 비밀번호 수정 [memberKey = {}]", memberKey);
        return MemberPasswordModifyResponse.of(currentDateTime);
    }

    public MemberTelModifyResponse modifyTel(String memberKey, LocalDateTime currentDateTime, MemberTelModifyServiceRequest request) {
        Member member = memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        if (member.telEquals(request.getTel())) {
            log.warn("회원 연락처 수정: 현재 연락처와 동일 [tel = {}]", request.getTel());
            throw MemberException.of(NOT_CHANGE_TEL);
        }

        if (memberRepository.existsByTel(request.getTel())) {
            log.warn("회원 연락처 수정: 연락처 중복 [tel = {}]", request.getTel());
            throw MemberException.of(DUPLICATE_TEL);
        }

        member.modifyTel(request.getTel());

        log.info("회원 연락처 수정 [memberKey = {}]", memberKey);
        return MemberTelModifyResponse.of(member.getTel(), currentDateTime);
    }

    public RegisterBusinessNumberResponse registerBusinessNumber(String memberKey, LocalDateTime current, RegisterBusinessNumberServiceRequest request) {
        Member member = memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        if (member.isBusiness()) {
            log.warn("회원 사업자 번호 등록: 등록된 사업자 번호 존재 [role = {}, businessNumber = {}]", member.getSpecificInfo().getRole(), member.getBusinessNumber());
            throw MemberException.of(HAS_BUSINESS_NUMBER);
        }

        if (memberRepository.existsByUserAdditionalInfoBusinessNumber(request.getBusinessNumber())) {
            log.warn("회원 사업자 번호 등록: 사업자 번호 중복 [businessNumber = {}]", request.getBusinessNumber());
            throw MemberException.of(DUPLICATE_BUSINESS_NUMBER);
        }

        member.registerBusinessNumber(request.getBusinessNumber());

        log.info("회원 사업자 번호 등록 [memberKey = {}, businessNumber = {}]", memberKey, request.getBusinessNumber());
        return RegisterBusinessNumberResponse.of(member.getBusinessNumber(), current);
    }

    public MemberBankAccountModifyResponse modifyBankAccount(String memberKey, LocalDateTime currentDateTime, MemberBankAccountModifyServiceRequest request) {
        Member member = memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        if (member.isNotBusiness()) {
            log.warn("회원 은행 계좌 수정: 은행 계좌 등록 권한이 없음 [role = {}]", member.getSpecificInfo().getRole());
            throw MemberException.of(NOT_BUSINESS_MEMBER);
        }

        BankAccount bankAccount = member.modifyBankAccount(request.getBankCode(), request.getAccountNumber());

        log.info("회원 은행 계좌 수정 [memberKey = {}]", memberKey);
        return MemberBankAccountModifyResponse.of(bankAccount, currentDateTime);
    }

    public MemberRemoveResponse removeMember(String memberKey, String password, LocalDateTime currentDateTime) {
        Member member = memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        if (member.isNotMatchesPwd(password)) {
            log.debug("회원 탈퇴: 비밀번호 불일치 [password = {}]", password);
            throw MemberException.of(NOT_MATCH_PASSWORD);
        }

        member.remove();

        log.info("회원 탈퇴 [memberKey = {}]", member);
        return MemberRemoveResponse.of(currentDateTime);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(Email.of(email))
            .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));

        return member.toUser();
    }
}
