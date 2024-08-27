package com.ssafy.auction_service.api.controller.variety.request;

import com.ssafy.auction_service.api.service.variety.request.VarietyCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VarietyCreateRequest {

    @NotBlank(message = "화훼부류를 입력해주세요.")
    private String category;

    @NotBlank(message = "품목명을 입력해주세요.")
    private String itemName;

    @NotBlank(message = "품종명을 입력해주세요.")
    private String varietyName;

    @Builder
    private VarietyCreateRequest(String category, String itemName, String varietyName) {
        this.category = category;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }

    public VarietyCreateServiceRequest toServiceRequest() {
        return VarietyCreateServiceRequest.of(category, itemName, varietyName);
    }
}
