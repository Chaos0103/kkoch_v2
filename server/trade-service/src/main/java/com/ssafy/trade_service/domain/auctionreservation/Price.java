package com.ssafy.trade_service.domain.auctionreservation;

import com.ssafy.trade_service.common.exception.AppException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Price {

    private final int value;

    @Builder
    private Price(int value) {
        if (value < 0) {
            throw new AppException("금액은 0원 이상이어야 합니다.");
        }

        if (value % 10 > 0) {
            throw new AppException("금액은 10원 단위이어야 합니다.");
        }
        this.value = value;
    }

    public static Price of(int value) {
        return new Price(value);
    }
}
