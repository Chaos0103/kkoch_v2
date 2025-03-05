package com.ssafy.boardservice.config;

import com.ssafy.common.global.exception.TokenException;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.ssafy.common.global.exception.code.ErrorCode.TOKEN_EXPIRED;
import static org.springframework.util.StringUtils.hasText;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                throw new TokenException(TOKEN_EXPIRED);
            }

            HttpServletRequest request = attributes.getRequest();
            String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (!hasText(accessToken)) {
                throw new TokenException(TOKEN_EXPIRED);
            }

            requestTemplate.header(HttpHeaders.AUTHORIZATION, accessToken);
        };
    }
}
