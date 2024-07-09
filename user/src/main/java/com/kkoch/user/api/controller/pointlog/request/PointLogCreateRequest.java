package com.kkoch.user.api.controller.pointlog.request;

import com.kkoch.user.api.service.pointlog.request.PointLogCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class PointLogCreateRequest {

    @NotBlank(message = "은행을 입력해주세요.")
    private String bank;

    @Positive(message = "금액을 올바르게 입력해주세요.")
    private int amount;

    @NotBlank(message = "상태를 입력해주세요.")
    private String status;

    @Builder
    private PointLogCreateRequest(String bank, int amount, String status) {
        this.bank = bank;
        this.amount = amount;
        this.status = status;
    }

    public PointLogCreateServiceRequest toServiceRequest() {
        return PointLogCreateServiceRequest.builder()
            .bank(bank)
            .amount(amount)
            .status(status)
            .build();
    }
}
