package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.common.exception.NotSupportedException;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.ssafy.auction_service.common.util.TimeUtils.isEqualsOrPast;
import static com.ssafy.auction_service.common.util.TimeUtils.parse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AuctionScheduleUtils {

    public static boolean validatePlantCategory(String plantCategory) {
        if (PlantCategory.isNotSupported(plantCategory)) {
            throw new NotSupportedException("지원하지 않는 화훼부류입니다.");
        }

        return true;
    }

    public static boolean validateJointMarket(String jointMarket) {
        if (JointMarket.isNotSupported(jointMarket)) {
            throw new NotSupportedException("지원하지 않는 공판장입니다.");
        }

        return true;
    }

    public static boolean validateAuctionStartDateTime(String auctionStartDateTime, LocalDateTime current) {
        LocalDateTime target = parse(auctionStartDateTime);

        if (isEqualsOrPast(target, current)) {
            throw new AppException("경매 시작 일시를 올바르게 입력해주세요.");
        }

        return true;
    }
}
