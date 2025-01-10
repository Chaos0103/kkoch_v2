package com.ssafy.userservice.domain.member.repository;

import com.ssafy.userservice.domain.member.Member;
import com.ssafy.userservice.domain.member.vo.BusinessNumber;
import com.ssafy.userservice.domain.member.vo.Email;
import com.ssafy.userservice.domain.member.vo.Tel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(Email email);

    boolean existsByTel(Tel tel);

    boolean existsByUserAdditionalInfoBusinessNumber(BusinessNumber businessNumber);

    Optional<Member> findBySpecificInfoMemberKey(String memberKey);

    Optional<Member> findByEmail(Email email);
}
