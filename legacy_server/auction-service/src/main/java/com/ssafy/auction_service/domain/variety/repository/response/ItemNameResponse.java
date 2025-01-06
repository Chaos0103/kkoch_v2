package com.ssafy.auction_service.domain.variety.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemNameResponse {

    private String itemName;

    @Builder
    private ItemNameResponse(String itemName) {
        this.itemName = itemName;
    }
}
