package com.ssafy.trade_service.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PickUp {

    @Column(nullable = false)
    private final Boolean isPickUp;

    @Column
    private final LocalDateTime pickUpDateTime;

    @Builder
    private PickUp(Boolean isPickUp, LocalDateTime pickUpDateTime) {
        this.isPickUp = isPickUp;
        this.pickUpDateTime = pickUpDateTime;
    }

    public static PickUp of(Boolean isPickUp, LocalDateTime pickUpDateTime) {
        return new PickUp(isPickUp, pickUpDateTime);
    }

    public static PickUp init() {
        return of(false, null);
    }

    public static PickUp pickUp(LocalDateTime current) {
        return of(true, current);
    }
}
