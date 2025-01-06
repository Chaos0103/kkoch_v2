package com.ssafy.auction_service.common.exception;

public class StringFormatException extends AppException {

    public StringFormatException(String message) {
        super(message);
    }

    public StringFormatException() {
    }

    public StringFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public StringFormatException(Throwable cause) {
        super(cause);
    }

    public StringFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
