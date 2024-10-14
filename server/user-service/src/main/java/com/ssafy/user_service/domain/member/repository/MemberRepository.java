package com.ssafy.user_service.domain.member.repository;

import com.ssafy.user_service.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    String DUPLICATED_EMAIL = "이미 가입된 이메일입니다.";
    String DUPLICATED_TEL = "이미 가입된 연락처입니다.";
    String DUPLICATED_BUSINESS_NUMBER = "이미 가입된 사업자 번호입니다.";
    String NO_SUCH_MEMBER = "등록되지 않은 회원입니다.";

    boolean existsByEmail(String email);

    boolean existsByTel(String tel);

    boolean existsByUserAdditionalInfoBusinessNumber(String businessNumber);

    Optional<Member> findBySpecificInfoMemberKey(String memberKey);

    Optional<Member> findByEmail(String email);
}
