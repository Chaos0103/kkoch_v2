package com.kkoch.admin.domain.bidresult.repository.dto;

import com.kkoch.admin.domain.Grade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BidResultDto {

    private String varietyCode;
    private Grade grade;
    private int plantCount;
    private int bidPrice;

    @Builder
    private BidResultDto(String varietyCode, Grade grade, int plantCount, int bidPrice) {
        this.varietyCode = varietyCode;
        this.grade = grade;
        this.plantCount = plantCount;
        this.bidPrice = bidPrice;
    }
}
