package com.ssafy.user_service.api.service;

import com.ssafy.user_service.common.exception.AppException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class SearchPeriod {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    @Builder
    private SearchPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static SearchPeriod of(LocalDate startDate, LocalDate endDate) {
        if (endDate.isAfter(startDate)) {
            throw new AppException();
        }
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay().plusDays(1);
        return new SearchPeriod(startDateTime, endDateTime);
    }
}
