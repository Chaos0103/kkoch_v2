package com.ssafy.apigateway_service.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private final int code;
    private final HttpStatus status;
    private final String message;
    private final T data;

    private ApiResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }

    @Override
    public String toString() {
        return "{" +
            "\"code\": " + code +
            ", \"status\": \"" + status.getReasonPhrase() + '\"' +
            ", \"message\": \"" + message + '\"' +
            ", \"data\": " + data +
            '}';
    }
}
