package com.ssafy.auction_service.common.exception;

public class NotSupportedException extends AppException {

    public NotSupportedException(String message) {
        super(message);
    }

    public NotSupportedException() {
    }

    public NotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportedException(Throwable cause) {
        super(cause);
    }

    public NotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
