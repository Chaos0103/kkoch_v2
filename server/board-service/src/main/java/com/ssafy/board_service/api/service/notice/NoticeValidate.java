package com.ssafy.board_service.api.service.notice;

import com.ssafy.board_service.api.service.StringValidate;
import com.ssafy.board_service.common.exception.AppException;
import com.ssafy.board_service.common.exception.LengthOutOfRangeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeValidate {

    private static final int MAX_NOTICE_TITLE_LENGTH = 50;

    public static boolean validateNoticeTitle(String targetNoticeTitle) {
        StringValidate noticeTitle = StringValidate.of(targetNoticeTitle);

        if (noticeTitle.isLengthMoreThan(MAX_NOTICE_TITLE_LENGTH)) {
            throw new LengthOutOfRangeException("공지사항 제목의 길이는 최대 50자리 입니다.");
        }

        return true;
    }

    public static boolean validateToFixedDateTime(LocalDateTime targetToFixedDateTime, LocalDateTime currentDateTime) {
        if (targetToFixedDateTime == null) {
            return true;
        }

        if (currentDateTime.isAfter(targetToFixedDateTime)) {
            throw new AppException("고정 종료일시를 올바르게 입력해주세요.");
        }

        return true;
    }
}
