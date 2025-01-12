package com.ssafy.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final String AUTHORIZATION_TYPE = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getTokenByHeader(request);
        if (isNotNull(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private String getTokenByHeader(ServletRequest servletRequest) {
        if (servletRequest instanceof HttpServletRequest request) {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(token) && token.startsWith(AUTHORIZATION_TYPE)) {
                return token.substring(7);
            }
        }
        return null;
    }

    private boolean isNotNull(String token) {
        return token != null;
    }
}
