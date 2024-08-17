package com.ssafy.user_service.common.security;

import com.ssafy.user_service.common.exception.AppException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class SecurityUtils {

    public static String findMemberKeyByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new AppException("토큰 정보가 없습니다.");
        }
        return authentication.getName();
    }
}
