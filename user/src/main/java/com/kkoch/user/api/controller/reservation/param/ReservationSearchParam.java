package com.kkoch.user.api.controller.reservation.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class ReservationSearchParam {

    @Positive(message = "페이지 번호를 올바르게 입력해주세요.")
    private int page = 1;

    @Builder
    private ReservationSearchParam(int page) {
        this.page = page;
    }

    public Pageable getPage() {
        return PageRequest.of(page - 1, 15);
    }
}
