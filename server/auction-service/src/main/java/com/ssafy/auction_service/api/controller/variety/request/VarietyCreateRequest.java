package com.ssafy.auction_service.api.controller.variety.request;

import com.ssafy.auction_service.api.service.variety.request.VarietyCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.auction_service.api.controller.variety.message.VarietyBindingMessage.*;

@Getter
@NoArgsConstructor
public class VarietyCreateRequest {

    @NotBlank(message = NOT_BLANK_PLANT_CATEGORY)
    private String category;

    @NotBlank(message = NOT_BLANK_ITEM_NAME)
    private String itemName;

    @NotBlank(message = NOT_BLANK_VARIETY_NAME)
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
