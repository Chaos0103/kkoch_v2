package com.kkoch.user.domain.pointlog.repository.response;

import com.kkoch.user.domain.pointlog.Bank;
import com.kkoch.user.domain.pointlog.PointStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PointLogResponse {

    private long pointLogId;
    private Bank bank;
    private int amount;
    private PointStatus status;
    private LocalDateTime createdDate;

    @Builder
    private PointLogResponse(long pointLogId, Bank bank, int amount, PointStatus status, LocalDateTime createdDate) {
        this.pointLogId = pointLogId;
        this.bank = bank;
        this.amount = amount;
        this.status = status;
        this.createdDate = createdDate;
    }
}
