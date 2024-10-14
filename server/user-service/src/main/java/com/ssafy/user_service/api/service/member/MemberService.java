package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.api.service.member.request.MemberBankAccountModifyServiceRequest;
import com.ssafy.user_service.api.service.member.request.MemberCreateServiceRequest;
import com.ssafy.user_service.api.service.member.request.MemberPasswordModifyServiceRequest;
import com.ssafy.user_service.api.service.member.request.MemberTelModifyServiceRequest;
import com.ssafy.user_service.api.service.member.response.*;
import com.ssafy.user_service.common.exception.AppException;
import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.ssafy.user_service.domain.member.repository.MemberRepository.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberCreateResponse createMember(MemberCreateServiceRequest request) {
        checkDuplicateEmail(request.getEmail());
        checkDuplicateTel(request.getTel());

        Member member = request.toEntity(passwordEncoder);
        Member savedMember = memberRepository.save(member);

        return MemberCreateResponse.of(savedMember);
    }

    public MemberPasswordModifyResponse modifyPassword(String memberKey, LocalDateTime currentDateTime, MemberPasswordModifyServiceRequest request) {
        Member member = findMemberBy(memberKey);

        checkMatchesPassword(member, request.getCurrentPassword());

        modifyPassword(member, request.getNewPassword());

        return MemberPasswordModifyResponse.of(currentDateTime);
    }

    public MemberTelModifyResponse modifyTel(String memberKey, LocalDateTime currentDateTime, MemberTelModifyServiceRequest request) {
        Member member = findMemberBy(memberKey);

        checkDuplicateTel(request.getTel());

        member.modifyTel(request.getTel());

        return MemberTelModifyResponse.of(member.getTel(), currentDateTime);
    }

    public MemberBankAccountModifyResponse modifyBankAccount(String memberKey, LocalDateTime currentDateTime, MemberBankAccountModifyServiceRequest request) {
        Member member = findMemberBy(memberKey);

        member.modifyBankAccount(request.getBankCode(), request.getAccountNumber());

        return MemberBankAccountModifyResponse.of(request.getBankCode(), request.getAccountNumber(), currentDateTime);
    }

    public MemberRemoveResponse removeMember(String memberKey, String password, LocalDateTime currentDateTime) {
        Member member = findMemberBy(memberKey);

        checkMatchesPassword(member, password);

        member.remove();

        return MemberRemoveResponse.of(currentDateTime);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("계정을 확인해주세요."));

        return User.builder()
            .username(member.getMemberKey())
            .password(member.getPwd())
            .roles(member.getSpecificInfo().getRole().toString())
            .build();
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

    private void checkDuplicateEmail(String email) {
        boolean isExistEmail = memberRepository.existsByEmail(email);
        if (isExistEmail) {
            throw new AppException(DUPLICATED_EMAIL);
        }
    }

    private void checkDuplicateTel(String tel) {
        boolean isExistTel = memberRepository.existsByTel(tel);
        if (isExistTel) {
            throw new AppException(DUPLICATED_TEL);
        }
    }

    private void checkDuplicateBusinessNumber(String businessNumber) {
        boolean isExistBusinessNumber = memberRepository.existsByUserAdditionalInfoBusinessNumber(businessNumber);
        if (isExistBusinessNumber) {
            throw new AppException(DUPLICATED_BUSINESS_NUMBER);
        }
    }

    private Member findMemberBy(String memberKey) {
        return memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));
    }

    private String generateEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
