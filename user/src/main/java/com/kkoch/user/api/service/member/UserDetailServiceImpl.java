package com.kkoch.user.api.service.member;

import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("등록되지 않는 사용자입니다."));

        return new User(member.getEmail(), member.getPwd(),
            true, true, true, true,
            new ArrayList<>());
    }


}
