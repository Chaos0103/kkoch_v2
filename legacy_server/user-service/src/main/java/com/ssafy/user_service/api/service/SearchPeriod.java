package com.ssafy.user_service.api.service;

import com.ssafy.user_service.common.exception.AppException;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.ssafy.user_service.common.util.StringUtils.isBlank;
import static com.ssafy.user_service.common.util.TimeUtils.*;

public class SearchPeriod {

    private final String from;
    private final String to;

    @Builder
    private SearchPeriod(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public static SearchPeriod of(String from, String to) {
        return new SearchPeriod(from, to);
    }

    public LocalDateTime getFrom() {
        if (isBlank(from)) {
            return null;
        }

        LocalDate fromDate = parse(from);

        return atStartOfDay(fromDate);
    }

    public LocalDateTime getTo() {
        if (isBlank(to)) {
            return null;
        }

        LocalDate toDate = parse(to);

        return atEndOfDay(toDate);
    }

    public boolean valid() {
        LocalDateTime fromDateTime = getFrom();
        LocalDateTime toDateTime = getTo();

        if (isNull(fromDateTime) && isNull(toDateTime)) {
            return true;
        }

        if (isNull(fromDateTime)) {
            throw new AppException("시작 일자를 입력해주세요.");
        }

        if (isNull(toDateTime)) {
            throw new AppException("종료 일자를 입력해주세요.");
        }

        if (getTo().isBefore(getFrom())) {
            throw new AppException("날짜를 올바르게 입력해주세요.");
        }
        return true;
    }
}
