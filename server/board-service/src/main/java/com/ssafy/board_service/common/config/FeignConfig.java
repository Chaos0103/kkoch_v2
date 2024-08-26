package com.ssafy.board_service.common.config;

import com.ssafy.board_service.common.exception.AppException;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.springframework.util.StringUtils.*;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                throw new AppException();
            }

            HttpServletRequest request = attributes.getRequest();
            String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (!hasText(accessToken)) {
                throw new AppException();
            }

            requestTemplate.header(HttpHeaders.AUTHORIZATION, accessToken);
        };
    }
}
