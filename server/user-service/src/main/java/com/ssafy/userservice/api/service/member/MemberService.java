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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder encoder;

    public MemberCreateResponse createMember(MemberCreateServiceRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw MemberException.of(DUPLICATE_EMAIL);
        }

        if (memberRepository.existsByTel(request.getTel())) {
            throw MemberException.of(DUPLICATE_TEL);
        }

        Member member = request.toEntity(encoder);
        Member savedMember = memberRepository.save(member);

        return MemberCreateResponse.of(savedMember);
    }

    public MemberPasswordModifyResponse modifyPassword(String memberKey, LocalDateTime currentDateTime, MemberPasswordModifyServiceRequest request) {
        Member member = memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        if (member.isNotMatchesPwd(request.getCurrentPassword(), encoder)) {
            throw MemberException.of(NOT_MATCH_PASSWORD);
        }

        member.modifyPassword(request.getNewPassword(), encoder);

        return MemberPasswordModifyResponse.of(currentDateTime);
    }

    public MemberTelModifyResponse modifyTel(String memberKey, LocalDateTime currentDateTime, MemberTelModifyServiceRequest request) {
        Member member = memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        if (member.telEquals(request.getTel())) {
            throw MemberException.of(NOT_CHANGE_TEL);
        }

        if (memberRepository.existsByTel(request.getTel())) {
            throw MemberException.of(DUPLICATE_TEL);
        }

        member.modifyTel(request.getTel());

        return MemberTelModifyResponse.of(member.getTel(), currentDateTime);
    }

    public RegisterBusinessNumberResponse registerBusinessNumber(String memberKey, LocalDateTime current, RegisterBusinessNumberServiceRequest request) {
        Member member = memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        if (member.hasBusinessNumber()) {
            throw MemberException.of(HAS_BUSINESS_NUMBER);
        }

        if (memberRepository.existsByUserAdditionalInfoBusinessNumber(request.getBusinessNumber())) {
            throw MemberException.of(DUPLICATE_BUSINESS_NUMBER);
        }

        member.registerBusinessNumber(request.getBusinessNumber());

        return RegisterBusinessNumberResponse.of(member.getBusinessNumber(), current);
    }

    public MemberBankAccountModifyResponse modifyBankAccount(String memberKey, LocalDateTime currentDateTime, MemberBankAccountModifyServiceRequest request) {
        Member member = memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        if (member.isNotBusiness()) {
            throw MemberException.of(NOT_BUSINESS_MEMBER);
        }

        BankAccount bankAccount = member.modifyBankAccount(request.getBankCode(), request.getAccountNumber());

        return MemberBankAccountModifyResponse.of(bankAccount, currentDateTime);
    }

    public MemberRemoveResponse removeMember(String memberKey, String password, LocalDateTime currentDateTime) {
        Member member = memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        if (member.isNotMatchesPwd(password, encoder)) {
            throw MemberException.of(NOT_MATCH_PASSWORD);
        }

        member.remove();

        return MemberRemoveResponse.of(currentDateTime);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(Email.of(email))
            .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));

        return member.toUser();
    }
}
