package com.ssafy.auction_service.api.controller.variety.param;

import com.ssafy.auction_service.domain.variety.repository.cond.VarietySearchCond;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ssafy.auction_service.api.controller.variety.message.VarietyBindingMessage.NOT_BLANK_PLANT_CATEGORY;
import static com.ssafy.auction_service.common.util.PageUtils.PARAM_DEFAULT_PAGE_SIZE;

@Getter
@Setter
@NoArgsConstructor
public class VarietySearchParam {

    private String page = PARAM_DEFAULT_PAGE_SIZE;

    @NotBlank(message = NOT_BLANK_PLANT_CATEGORY)
    private String plantCategory;

    private String itemName;

    @Builder
    private VarietySearchParam(String page, String plantCategory, String itemName) {
        this.page = page;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
    }

    public VarietySearchCond toCond() {
        return VarietySearchCond.of(plantCategory, itemName);
    }
}
