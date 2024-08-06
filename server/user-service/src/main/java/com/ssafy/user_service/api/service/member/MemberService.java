package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.api.service.member.request.MemberCreateServiceRequest;
import com.ssafy.user_service.api.service.member.response.MemberCreateResponse;
import com.ssafy.user_service.common.exception.AppException;
import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.user_service.api.service.member.MemberValidate.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberCreateResponse createUserMember(MemberCreateServiceRequest request) {
        boolean isExistEmail = memberRepository.existsByEmail(request.getEmail());
        if (isExistEmail) {
            throw new AppException("이미 가입된 이메일입니다.");
        }

        boolean isExistTel = memberRepository.existsByTel(request.getTel());
        if (isExistTel) {
            throw new AppException("이미 가입된 연락처입니다.");
        }

        boolean isExistBusinessNumber = memberRepository.existsByUserAdditionalInfoBusinessNumber(request.getBusinessNumber());
        if (isExistBusinessNumber) {
            throw new AppException("이미 가입된 사업자 번호입니다.");
        }

        validateEmail(request.getEmail());
        validatePassword(request.getPassword());
        validateName(request.getName());
        validateTel(request.getTel());
        validateBusinessNumber(request.getBusinessNumber());

        Member member = request.toEntity(passwordEncoder.encode(request.getPassword()));
        Member savedMember = memberRepository.save(member);

        return MemberCreateResponse.of(savedMember);
    }
}
