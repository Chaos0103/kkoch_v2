package com.kkoch.admin.domain.stats;

import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stats extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stats_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 8)
    private Grade grade;

    @Column(nullable = false, updatable = false)
    private int plantCount;

    @Embedded
    private Price price;

    @Column(nullable = false, updatable = false)
    private LocalDate statsDate;

    @Column(nullable = false, updatable = false, columnDefinition = "char(8)", length = 8)
    private String varietyCode;

    @Builder
    private Stats(boolean isDeleted, Grade grade, int plantCount, Price price, LocalDate statsDate, String varietyCode) {
        super(isDeleted);
        this.grade = grade;
        this.plantCount = plantCount;
        this.price = price;
        this.statsDate = statsDate;
        this.varietyCode = varietyCode;
    }

    public static Stats of(boolean isDeleted, Grade grade, int plantCount, Price price, LocalDate statsDate, String varietyCode) {
        return new Stats(isDeleted, grade, plantCount, price, statsDate, varietyCode);
    }

    public static Stats create(Grade grade, int plantCount, Price price, LocalDate statsDate, String varietyCode) {
        return of(false, grade, plantCount, price, statsDate, varietyCode);
    }
}
