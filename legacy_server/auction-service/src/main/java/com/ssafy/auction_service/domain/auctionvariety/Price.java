package com.ssafy.auction_service.domain.auctionvariety;

import com.ssafy.auction_service.common.exception.AppException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Price {

    @Column(name = "auction_start_price", nullable = false)
    private final int value;

    public Price(int value) {
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
