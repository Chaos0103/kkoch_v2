package com.kkoch.user.api.service.pointlog.response;

import com.kkoch.user.domain.pointlog.Bank;
import com.kkoch.user.domain.pointlog.PointLog;
import com.kkoch.user.domain.pointlog.PointStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PointLogCreateResponse {

    private final long pointLogId;
    private final Bank bank;
    private final int amount;
    private final PointStatus status;
    private final long balance;

    @Builder
    private PointLogCreateResponse(long pointLogId, Bank bank, int amount, PointStatus status, long balance) {
        this.pointLogId = pointLogId;
        this.bank = bank;
        this.amount = amount;
        this.status = status;
        this.balance = balance;
    }

    public static PointLogCreateResponse of(PointLog pointLog, long balance) {
        return new PointLogCreateResponse(pointLog.getId(), pointLog.getBank(), pointLog.getAmount(), pointLog.getStatus(), balance);
    }
}
