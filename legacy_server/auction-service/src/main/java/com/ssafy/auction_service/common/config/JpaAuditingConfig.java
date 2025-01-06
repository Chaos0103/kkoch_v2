package com.ssafy.auction_service.common.config;

import com.ssafy.auction_service.api.client.MemberServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
@RequiredArgsConstructor
public class JpaAuditingConfig {

    private final MemberServiceClient memberServiceClient;

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> {
            Long memberId = memberServiceClient.searchMemberId()
                .getData().getMemberId();
            return Optional.of(memberId);
        };
    }
}
