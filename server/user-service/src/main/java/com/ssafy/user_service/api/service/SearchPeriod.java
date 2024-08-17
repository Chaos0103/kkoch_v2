package com.ssafy.user_service.api.service;

import com.ssafy.user_service.common.exception.AppException;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SearchPeriod {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

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

        return fromDate.atStartOfDay();
    }

    public LocalDateTime getTo() {
        if (isBlank(to)) {
            return null;
        }

        LocalDate toDate = parse(to);

        return toDate.atStartOfDay()
            .plusDays(1)
            .minusSeconds(1);
    }

    public boolean valid() {
        LocalDateTime fromDateTime = getFrom();
        LocalDateTime toDateTime = getTo();

        if (fromDateTime == null && toDateTime == null) {
            return true;
        }

        if (fromDateTime == null) {
            throw new AppException("시작 일자를 입력해주세요.");
        }

        if (toDateTime == null) {
            throw new AppException("종료 일자를 입력해주세요.");
        }

        if (getTo().isBefore(getFrom())) {
            throw new AppException("날짜를 올바르게 입력해주세요.");
        }
        return true;
    }

    private LocalDate parse(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new AppException("날짜를 올바르게 입력해주세요.", e);
        }
    }

    private boolean isBlank(String date) {
        return StringValidate.of(date).isBlank();
    }
}
