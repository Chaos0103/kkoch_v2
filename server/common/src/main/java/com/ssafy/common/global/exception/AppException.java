package com.ssafy.common.global.exception;

import com.ssafy.common.global.exception.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    protected AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    protected AppException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
