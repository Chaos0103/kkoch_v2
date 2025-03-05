package com.ssafy.boardservice.domain.notice.vo;

import com.ssafy.common.global.exception.NoticeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_NOTICE_TITLE_LENGTH;
import static com.ssafy.common.global.utils.StringUtils.isLengthMoreThan;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class NoticeTitle {

    private static final int TITLE_LENGTH = 50;

    @Column(name = "title", nullable = false, length = TITLE_LENGTH)
    private final String value;

    private NoticeTitle(String value) {
        validation(value);
        this.value = value;
    }

    public static NoticeTitle of(String value) {
        return new NoticeTitle(value);
    }

    @Override
    public String toString() {
        return value;
    }

    private void validation(String value) {
        if (isLengthMoreThan(value, TITLE_LENGTH)) {
            throw NoticeException.of(INVALID_NOTICE_TITLE_LENGTH);
        }
    }
}
