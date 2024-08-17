package com.ssafy.user_service.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.user_service.api.controller.member.request.LoginRequest;
import com.ssafy.user_service.common.exception.AppException;
import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String CONTENT_TYPE = "application/json";
    private static final String CHARACTER_ENCODING = "utf-8";

    private final MemberRepository memberRepository;
    private final Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository, Environment environment) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new AppException("잠시후 다시 시도해주세요.", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
        Member member = findMember(auth);

        if (member.isDelete()) {
            throw new AppException("탈퇴된 계정입니다.");
        }

        String accessToken = generateAccessToken(member);

        Map<String, String> map = generateResponseBodyMap(accessToken, member);

        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        String value = new ObjectMapper().writeValueAsString(map);
        response.getWriter().write(value);
    }

    private Member findMember(Authentication auth) {
        String memberKey = ((User) auth.getPrincipal()).getUsername();
        return memberRepository.findBySpecificInfoMemberKey(memberKey)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 회원입니다."));
    }

    private String generateAccessToken(Member member) {
        Instant currentInstance = Instant.now();

        SecretKey secretKey = generateSecretKey();
        Instant expirationInstance = generateExpirationInstance(currentInstance);

        return Jwts.builder()
            .subject(member.getMemberKey())
            .expiration(Date.from(expirationInstance))
            .issuedAt(Date.from(currentInstance))
            .signWith(secretKey)
            .compact();
    }

    private SecretKey generateSecretKey() {
        String tokenSecret = environment.getProperty("token.secret");
        if (tokenSecret == null) {
            throw new IllegalArgumentException("잠시 서버에 문제가 발생했습니다.");
        }

        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());

        return Keys.hmacShaKeyFor(secretKeyBytes);
    }

    private Instant generateExpirationInstance(Instant current) {
        String tokenExpirationTime = environment.getProperty("token.expiration_time");
        if (tokenExpirationTime == null) {
            throw new IllegalArgumentException("잠시 서버에 문제가 발생했습니다.");
        }

        return current.plusMillis(Long.parseLong(tokenExpirationTime));
    }

    private Map<String, String> generateResponseBodyMap(String accessToken, Member member) {
        return Map.of(
            "accessToken", accessToken,
            "memberKey", member.getMemberKey()
        );
    }
}
