package com.kkoch.admin.domain.bidresult.repository.response;

import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BidResultResponse {

    private PlantCategory plantCategory;
    private String itemName;
    private String varietyName;
    private Grade grade;
    private int plantCount;
    private int bidPrice;
    private LocalDateTime bidDateTime;
    private String region;

    @Builder
    private BidResultResponse(PlantCategory plantCategory, String itemName, String varietyName, Grade grade, int plantCount, int bidPrice, LocalDateTime bidDateTime, String region) {
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
        this.grade = grade;
        this.plantCount = plantCount;
        this.bidPrice = bidPrice;
        this.bidDateTime = bidDateTime;
        this.region = region;
    }
}
