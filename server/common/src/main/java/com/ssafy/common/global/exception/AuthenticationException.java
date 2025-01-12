package com.ssafy.common.global.exception;

import com.ssafy.common.global.exception.code.ErrorCode;

public class AuthenticationException extends AppException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
