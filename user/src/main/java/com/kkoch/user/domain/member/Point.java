package com.kkoch.user.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Point {

    @Column(name = "point", nullable = false, columnDefinition = "int default 0")
    private final long value;

    @Builder
    private Point(long value) {
        this.value = value;
    }

    public static Point of(long value) {
        return new Point(value);
    }

    public static Point init() {
        return of(0);
    }

    public Point add(int amount) {
        return of(amount);
    }

    public Point subtract(int amount) {
        long calc = value - amount;
        if (calc < 0) {
            throw new IllegalArgumentException();
        }
        return of(calc);
    }
}
