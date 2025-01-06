package com.ssafy.user_service.common.config;

import com.ssafy.user_service.api.service.member.MemberService;
import com.ssafy.user_service.common.security.AuthenticationFilter;
import com.ssafy.user_service.common.security.JwtAuthenticationFilter;
import com.ssafy.user_service.common.security.JwtTokenProvider;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Environment environment;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(memberService)
            .passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/members", "POST")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/login", "POST")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/**")).hasAnyRole("MASTER", "USER", "ADMIN")
//                .requestMatchers(new AntPathRequestMatcher("/members/admin", "POST")).hasAnyRole("ADMIN")
//                .requestMatchers(new AntPathRequestMatcher("/notifications/sent", "GET")).hasAnyRole("ADMIN")
                .requestMatchers("/**").access(
                    new WebExpressionAuthorizationManager("hasIpAddress('127.0.0.1') or hasIpAddress('172.30.32.226')")
                )
                .anyRequest().authenticated()
            )
            .authenticationManager(authenticationManager)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(getAuthenticationFilter(authenticationManager));
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, memberRepository, environment);
    }
}
