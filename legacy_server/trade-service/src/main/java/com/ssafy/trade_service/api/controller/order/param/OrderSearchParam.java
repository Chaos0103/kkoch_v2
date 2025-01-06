package com.ssafy.trade_service.api.controller.order.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ssafy.trade_service.common.util.PageUtils.PARAM_DEFAULT_PAGE_SIZE;

@Getter
@Setter
@NoArgsConstructor
public class OrderSearchParam {

    private String page = PARAM_DEFAULT_PAGE_SIZE;

    @Builder
    private OrderSearchParam(String page) {
        this.page = page;
    }
}
