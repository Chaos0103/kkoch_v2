package com.ssafy.boardservice.domain.notice.vo;

import com.ssafy.common.global.exception.NoticeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_NOTICE_FIXED_DATE_TIME;
import static com.ssafy.common.global.utils.TimeUtils.isPast;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class NoticeFixedDateTime {

    @Column(name = "to_fixed_date_time", nullable = false)
    private final LocalDateTime value;

    private NoticeFixedDateTime(LocalDateTime value, LocalDateTime current) {
        validation(value, current);
        this.value = value;
    }

    public static NoticeFixedDateTime of(LocalDateTime value, LocalDateTime current) {
        return new NoticeFixedDateTime(value, current);
    }

    public void validation(LocalDateTime value, LocalDateTime current) {
        if (isPast(value, current)) {
            throw NoticeException.of(INVALID_NOTICE_FIXED_DATE_TIME);
        }
    }

    public boolean isFixed(LocalDateTime current) {
        return value.isAfter(current);
    }
}
