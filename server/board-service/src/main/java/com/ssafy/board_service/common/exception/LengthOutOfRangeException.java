package com.ssafy.board_service.common.exception;

public class LengthOutOfRangeException extends AppException {

    public LengthOutOfRangeException(String message) {
        super(message);
    }

    public LengthOutOfRangeException() {
    }

    public LengthOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LengthOutOfRangeException(Throwable cause) {
        super(cause);
    }

    public LengthOutOfRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
