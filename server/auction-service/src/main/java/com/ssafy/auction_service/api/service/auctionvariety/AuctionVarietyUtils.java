package com.ssafy.auction_service.api.service.auctionvariety;

import com.ssafy.auction_service.common.exception.LengthOutOfRangeException;
import com.ssafy.auction_service.common.exception.NotSupportedException;
import com.ssafy.auction_service.common.exception.StringFormatException;
import com.ssafy.auction_service.domain.auctionvariety.PlantGrade;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.ssafy.auction_service.common.util.StringUtils.isLengthMoreThan;
import static com.ssafy.auction_service.common.util.StringUtils.isNotKorean;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AuctionVarietyUtils {

    private static final int MAX_REGION_LENGTH = 20;
    private static final int MAX_SHIPPER_LENGTH = 20;
    private static final boolean SUCCESS = true;

    public static PlantGrade parsePlantGrade(String str) {
        try {
            return PlantGrade.valueOf(str);
        } catch (IllegalArgumentException e) {
            throw new NotSupportedException("지원하지 않는 화훼등급입니다.", e);
        }
    }

    public static boolean validateRegion(String region) {
        if (isLengthMoreThan(region, MAX_REGION_LENGTH)) {
            throw new LengthOutOfRangeException("출하 지역의 길이는 최대 20자리 입니다.");
        }

        if (isNotKorean(region)) {
            throw new StringFormatException("출하 지역을 올바르게 입력해주세요.");
        }
        return SUCCESS;
    }

    public static boolean validateShipper(String shipper) {
        if (isLengthMoreThan(shipper, MAX_SHIPPER_LENGTH)) {
            throw new LengthOutOfRangeException("출하자의 길이는 최대 20자리 입니다.");
        }

        if (isNotKorean(shipper)) {
            throw new StringFormatException("출하자를 올바르게 입력해주세요.");
        }
        return SUCCESS;
    }
}
