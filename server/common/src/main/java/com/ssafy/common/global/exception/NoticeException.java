package com.ssafy.common.global.exception;

import com.ssafy.common.global.exception.code.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import static com.ssafy.common.global.exception.code.ErrorCode.NOTICE_NOT_FOUND;

@Slf4j
public class NoticeException extends AppException {

    private NoticeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static NoticeException of(ErrorCode errorCode) {
        return new NoticeException(errorCode);
    }

    public static NoticeException notFound(Integer noticeId) {
        log.warn("공지사항 조회 실패 [noticeId = {}]", noticeId);
        return of(NOTICE_NOT_FOUND);
    }
}
