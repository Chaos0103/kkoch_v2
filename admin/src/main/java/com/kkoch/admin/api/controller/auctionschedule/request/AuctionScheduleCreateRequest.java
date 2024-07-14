package com.kkoch.admin.api.controller.auctionschedule.request;

import com.kkoch.admin.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionScheduleCreateRequest {

    @NotBlank(message = "화훼 코드를 입력해주세요.")
    private String code;

    @NotNull(message = "경매 일시를 입력해주세요.")
    private LocalDateTime auctionDateTime;

    @Builder
    private AuctionScheduleCreateRequest(String code, LocalDateTime auctionDateTime) {
        this.code = code;
        this.auctionDateTime = auctionDateTime;
    }

    public AuctionScheduleCreateServiceRequest toServiceRequest() {
        return AuctionScheduleCreateServiceRequest.of(code, auctionDateTime);
    }
}
