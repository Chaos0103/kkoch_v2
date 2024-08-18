package com.ssafy.apigateway_service.filter;

import com.ssafy.apigateway_service.response.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private static final String AUTHORIZATION_TYPE = "Bearer";

    private final Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (isNotContainsAuthorizationHeader(request)) {
                throw new UnauthorizedException("인증 헤더가 없습니다.");
            }

            List<String> authorizations = getAuthorizationHeaders(request);
            if (authorizations.isEmpty()) {
                throw new UnauthorizedException("인증 헤더가 없습니다.");
            }

            String jwt = getJwt(authorizations);

            if (!isJwtValid(jwt)) {
                throw new UnauthorizedException("토큰이 유효하지 않습니다.");
            }

            return chain.filter(exchange);
        };
    }

    private static String getJwt(List<String> authorizations) {
        String authorizationHeader = authorizations.get(0);
        return authorizationHeader.replace(AUTHORIZATION_TYPE, "").strip();
    }

    private List<String> getAuthorizationHeaders(ServerHttpRequest request) {
        List<String> authorizations = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        return authorizations == null ? new ArrayList<>() : authorizations;
    }

    private boolean isNotContainsAuthorizationHeader(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

    private boolean isJwtValid(String jwt) {
        String tokenSecret = env.getProperty("token.secret");
        if (tokenSecret == null) {
            log.error("token secret 환경 변수 오류");
            throw new IllegalArgumentException("잠시 서버에 문제가 발생했습니다.");
        }

        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());

        SecretKey signingKey = Keys.hmacShaKeyFor(secretKeyBytes);

        String subject = null;

        try {
            subject = getSubjectBy(signingKey, jwt);
        } catch (Exception e) {
            return false;
        }

        return isNotBlank(subject);
    }

    private String getSubjectBy(SecretKey signingKey, String token) {
        JwtParser jwtParser = Jwts.parser()
            .setSigningKey(signingKey)
            .build();

        Claims body = jwtParser.parseClaimsJws(token).getBody(); //빈칸 예외
        return body.getSubject();
    }

    private boolean isBlank(String subject) {
        return subject == null || subject.isBlank();
    }

    private boolean isNotBlank(String subject) {
        return !isBlank(subject);
    }
}
