package com.ssafy.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Base64;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Environment env;

    public JwtTokenProvider(Environment env) {
        this.env = env;
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        UserDetails principal = new User(claims.getSubject(), "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, "", new ArrayList<>());
    }

    private Claims parseClaims(String accessToken) {
        String tokenSecret = env.getProperty("token.secret");
        if (tokenSecret == null) {
            log.error("token secret 환경 변수 오류");
            throw new IllegalArgumentException("잠시 서버에 문제가 발생했습니다.");
        }

        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey signingKey = Keys.hmacShaKeyFor(secretKeyBytes);

        try {
            return Jwts.parser()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(accessToken.strip())
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
