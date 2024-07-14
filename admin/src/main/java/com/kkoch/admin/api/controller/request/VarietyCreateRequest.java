package com.kkoch.admin.api.controller.request;

import com.kkoch.admin.api.service.variety.request.VarietyCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class VarietyCreateRequest {

    @NotBlank(message = "화훼부류를 입력해주세요.")
    private String plantCategory;

    @NotBlank(message = "품목명을 입력해주세요.")
    private String itemName;

    @NotBlank(message = "품종명을 입력해주세요.")
    private String varietyName;

    @Builder
    private VarietyCreateRequest(String plantCategory, String itemName, String varietyName) {
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }

    public VarietyCreateServiceRequest toServiceRequest() {
        return VarietyCreateServiceRequest.of(plantCategory, itemName, varietyName);
    }
}
