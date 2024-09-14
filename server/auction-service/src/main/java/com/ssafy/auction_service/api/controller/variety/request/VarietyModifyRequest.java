package com.ssafy.auction_service.api.controller.variety.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.auction_service.api.controller.variety.message.VarietyBindingMessage.NOT_BLANK_VARIETY_NAME;

@Getter
@NoArgsConstructor
public class VarietyModifyRequest {

    @NotBlank(message = NOT_BLANK_VARIETY_NAME)
    private String varietyName;

    @Builder
    private VarietyModifyRequest(String varietyName) {
        this.varietyName = varietyName;
    }
}
