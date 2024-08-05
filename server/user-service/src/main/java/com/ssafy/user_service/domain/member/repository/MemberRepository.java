package com.ssafy.user_service.domain.member.repository;

import com.ssafy.user_service.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    boolean existsByTel(String tel);

    boolean existsByUserAdditionalInfoBusinessNumber(String businessNumber);
}
