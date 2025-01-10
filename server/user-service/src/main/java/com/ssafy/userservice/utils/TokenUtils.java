package com.ssafy.userservice.utils;

import com.ssafy.common.global.exception.TokenException;
import com.ssafy.common.global.exception.code.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TokenUtils {

    public static String getMemberKeyByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new TokenException(ErrorCode.TOKEN_EXPIRED);
        }
        return authentication.getName();
    }
}
