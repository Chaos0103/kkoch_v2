package com.kkoch.admin.api.controller.auctionschedule.request;

import com.kkoch.admin.api.service.auctionschedule.request.AuctionScheduleModifyServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionScheduleModifyRequest {

    @NotBlank(message = "화훼 코드를 입력해주세요.")
    private String code;

    @NotNull(message = "경매 일시를 입력해주세요.")
    private LocalDateTime auctionDateTime;

    @Builder
    private AuctionScheduleModifyRequest(String code, LocalDateTime auctionDateTime) {
        this.code = code;
        this.auctionDateTime = auctionDateTime;
    }

    public AuctionScheduleModifyServiceRequest toServiceRequest() {
        return AuctionScheduleModifyServiceRequest.of(code, auctionDateTime);
    }
}
