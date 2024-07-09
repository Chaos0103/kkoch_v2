package com.kkoch.user.api.controller.pointlog.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@NoArgsConstructor
public class PointLogSearchParam {

    private int page = 0;

    @Builder
    private PointLogSearchParam(int page) {
        this.page = page;
    }

    public Pageable getPage() {
        return PageRequest.of(page - 1, 15);
    }
}
