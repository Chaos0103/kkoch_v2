package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.api.service.member.request.*;
import com.ssafy.user_service.api.service.member.response.*;
import com.ssafy.user_service.common.exception.AppException;
import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import com.ssafy.user_service.domain.member.vo.UserAdditionalInfo;
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

    private static final String NOT_MATCHES_CURRENT_PWD = "비밀번호가 일치하지 않습니다.";

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberCreateResponse createMember(MemberCreateServiceRequest request) {
        if (request.isDuplicatedEmail(memberRepository)) {
            throw new AppException(DUPLICATED_EMAIL);
        }

        if (request.isDuplicatedTel(memberRepository)) {
            throw new AppException(DUPLICATED_TEL);
        }

        Member member = request.toEntity(passwordEncoder);
        Member savedMember = memberRepository.save(member);

        return MemberCreateResponse.of(savedMember);
    }

    public MemberPasswordModifyResponse modifyPassword(String memberKey, LocalDateTime currentDateTime, MemberPasswordModifyServiceRequest request) {
        Member member = findMemberBy(memberKey);

        if (request.isNotMatchesCurrentPwdOf(member, passwordEncoder)) {
            throw new AppException(NOT_MATCHES_CURRENT_PWD);
        }

        request.modifyPwdOf(member, passwordEncoder);

        return MemberPasswordModifyResponse.of(currentDateTime);
    }

    public MemberTelModifyResponse modifyTel(String memberKey, LocalDateTime currentDateTime, MemberTelModifyServiceRequest request) {
        Member member = findMemberBy(memberKey);

        if (request.isDuplicatedTel(memberRepository)) {
            throw new AppException(DUPLICATED_TEL);
        }

        request.modifyTelOf(member);

        return MemberTelModifyResponse.of(member.getTel(), currentDateTime);
    }

    public RegisterBusinessNumberResponse registerBusinessNumber(String memberKey, LocalDateTime current, RegisterBusinessNumberServiceRequest request) {
        Member member = findMemberBy(memberKey);

        if (member.hasBusinessNumber()) {
            throw new AppException("이미 사업자 번호가 등록되었습니다.");
        }

        if (request.isDuplicatedBusinessNumber(memberRepository)) {
            throw new AppException(DUPLICATED_BUSINESS_NUMBER);
        }

        request.registerBusinessNumber(member);

        return RegisterBusinessNumberResponse.of(member.getBusinessNumber(), current);
    }

    public MemberAdditionalInfoModifyResponse modifyUserAdditionalInfo(String memberKey, LocalDateTime currentDateTime, MemberUserAdditionalInfoModifyServiceRequest request) {
        checkDuplicateBusinessNumber(request.getBusinessNumber());

        Member member = findMemberBy(memberKey);

        UserAdditionalInfo userAdditionalInfo = request.createUserAdditionalInfo();
        member.modifyUserAdditionalInfo(userAdditionalInfo);

        return MemberAdditionalInfoModifyResponse.of(request.getBankCode(), request.getAccountNumber(), currentDateTime);
    }

    public MemberRemoveResponse removeMember(String memberKey, String password, LocalDateTime currentDateTime) {
        Member member = findMemberBy(memberKey);

        if (member.isNotMatchesPwd(passwordEncoder, password)) {
            throw new AppException(NOT_MATCHES_CURRENT_PWD);
        }

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
}
