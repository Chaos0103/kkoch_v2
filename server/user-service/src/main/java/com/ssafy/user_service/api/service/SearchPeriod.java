package com.ssafy.user_service.api.service;

import com.ssafy.user_service.common.exception.AppException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class SearchPeriod {

    private final LocalDateTime from;
    private final LocalDateTime to;

    @Builder
    private SearchPeriod(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public static SearchPeriod of(LocalDate fromDate, LocalDate toDate) {
        if (toDate.isAfter(fromDate)) {
            throw new AppException();
        }

        LocalDateTime from = fromDate.atStartOfDay();
        LocalDateTime to = toDate.atStartOfDay().plusDays(1).minusSeconds(1);
        return new SearchPeriod(from, to);
    }
}
