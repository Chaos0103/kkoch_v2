package com.ssafy.apigateway_service.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ApiResponse<Object> unauthorizedException(UnauthorizedException e) {
        return ApiResponse.of(
            HttpStatus.UNAUTHORIZED,
            e.getMessage(),
            null
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> exception(Exception e) {
        log.error(e.getMessage());
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            "잠시후에 다시 시도해주세요.",
            null
        );
    }
}
