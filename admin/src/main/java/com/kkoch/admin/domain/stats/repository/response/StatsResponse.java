package com.kkoch.admin.domain.stats.repository.response;

import com.kkoch.admin.domain.Grade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StatsResponse {

    private int avgPrice;
    private int maxPrice;
    private int minPrice;
    private Grade grade;
    private int plantCount;
    private String itemName;
    private String varietyName;
    private LocalDateTime createdDateTime;

    @Builder
    private StatsResponse(int avgPrice, int maxPrice, int minPrice, Grade grade, int plantCount, String itemName, String varietyName, LocalDateTime createdDateTime) {
        this.avgPrice = avgPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.grade = grade;
        this.plantCount = plantCount;
        this.itemName = itemName;
        this.varietyName = varietyName;
        this.createdDateTime = createdDateTime;
    }
}
