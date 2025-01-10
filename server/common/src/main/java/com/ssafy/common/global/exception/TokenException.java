package com.ssafy.common.global.exception;

import com.ssafy.common.global.exception.code.ErrorCode;

public class TokenException extends AppException {

  public TokenException(ErrorCode errorCode) {
    super(errorCode);
  }
}
