package com.ssafy.auction_service.api.service.variety;

import com.ssafy.auction_service.common.exception.LengthOutOfRangeException;
import com.ssafy.auction_service.common.exception.NotSupportedException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.ssafy.auction_service.common.util.StringUtils.isLengthMoreThan;
import static com.ssafy.auction_service.domain.variety.PlantCategory.isNotSupported;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class VarietyUtils {

    private static final int MAX_ITEM_NAME_LENGTH = 20;
    private static final int MAX_VARIETY_NAME_LENGTH = 20;
    private static final boolean SUCCESS = true;

    public static boolean validatePlantCategory(String plantCategory) {
        if (isNotSupported(plantCategory)) {
            throw new NotSupportedException("지원하지 않는 화훼부류입니다.");
        }

        return SUCCESS;
    }

    public static boolean validateItemName(String itemName) {
        if (isLengthMoreThan(itemName, MAX_ITEM_NAME_LENGTH)) {
            throw new LengthOutOfRangeException("품목명의 길이는 최대 20자리 입니다.");
        }

        return SUCCESS;
    }

    public static boolean validateVarietyName(String varietyName) {
        if (isLengthMoreThan(varietyName, MAX_VARIETY_NAME_LENGTH)) {
            throw new LengthOutOfRangeException("품종명의 길이는 최대 20자리 입니다.");
        }

        return SUCCESS;
    }
}
