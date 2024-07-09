package com.kkoch.user.api.service.pointlog.request;

import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.pointlog.Bank;
import com.kkoch.user.domain.pointlog.PointLog;
import com.kkoch.user.domain.pointlog.PointStatus;
import lombok.Builder;

public class PointLogCreateServiceRequest {

    private final String bank;
    private final int amount;
    private final String status;

    @Builder
    private PointLogCreateServiceRequest(String bank, int amount, String status) {
        this.bank = bank;
        this.amount = amount;
        this.status = status;
    }

    public PointLog toEntity(Member member) {
        Bank bank = Bank.valueOf(this.bank);
        PointStatus status = PointStatus.valueOf(this.status);
        return PointLog.create(bank, amount, status, member);
    }
}
