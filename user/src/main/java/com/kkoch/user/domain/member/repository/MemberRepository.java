package com.kkoch.user.domain.member.repository;

import com.kkoch.user.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    boolean existsByTel(String tel);

    boolean existsByBusinessNumber(String businessNumber);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberKey(String memberKey);
}
