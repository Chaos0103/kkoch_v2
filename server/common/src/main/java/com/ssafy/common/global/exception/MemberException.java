package com.ssafy.common.global.exception;

import com.ssafy.common.global.exception.code.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberException extends AppException {

    private MemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static MemberException of(ErrorCode errorCode) {
        return new MemberException(errorCode);
    }

    public static MemberException notFound(String memberKey) {
        log.warn("회원 조회 실패 [memberKey = {}]", memberKey);
        return of(ErrorCode.MEMBER_NOT_FOUND);
    }
}
