package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.common.exception.AppException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.ssafy.auction_service.common.util.TimeUtils.isEqualsOrPast;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AuctionScheduleUtils {

    public static boolean validateAuctionStartDateTime(LocalDateTime auctionStartDateTime, LocalDateTime current) {
        if (isEqualsOrPast(auctionStartDateTime, current)) {
            throw new AppException("경매 시작 일시를 올바르게 입력해주세요.");
        }

        return true;
    }
}
