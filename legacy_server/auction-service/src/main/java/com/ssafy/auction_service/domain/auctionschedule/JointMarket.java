package com.ssafy.auction_service.domain.auctionschedule;

import com.ssafy.auction_service.common.exception.NotSupportedException;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import lombok.Getter;

import java.util.Set;

import static com.ssafy.auction_service.domain.variety.PlantCategory.*;

@Getter
public enum JointMarket {

    YANGJAE("aT화훼공판장", "양재동", Set.of(CUT_FLOWERS, ORCHID, FOLIAGE)),
    EOMGUNG("부산화훼공판장", "엄궁동", Set.of(CUT_FLOWERS, ORCHID, FOLIAGE)),
    GANGDONG("부산경남화훼농협", "강동동", Set.of(CUT_FLOWERS, ORCHID, FOLIAGE)),
    GWANGJU("광주원예농협", "풍암", Set.of(CUT_FLOWERS, ORCHID)),
    EUMSEONG("한국화훼농협", "음성", Set.of(ORCHID, FOLIAGE)),
    GOYANG("한국화훼농협", "고양", Set.of(ORCHID)),
    GIMHAE("영난화훼농협", "김해", Set.of(CUT_FLOWERS));

    private static final String NOT_SUPPORTED_VALUE = "지원하지 않는 공판장입니다.";
    private static final String FULL_NAME_FORMAT = "%s(%s)";

    private final String name;
    private final String city;
    private final Set<PlantCategory> auctionPlantCategories;

    JointMarket(String name, String city, Set<PlantCategory> auctionPlantCategories) {
        this.name = name;
        this.city = city;
        this.auctionPlantCategories = auctionPlantCategories;
    }

    public static JointMarket of(String str) {
        try {
            return JointMarket.valueOf(str);
        } catch (IllegalArgumentException e) {
            throw new NotSupportedException(NOT_SUPPORTED_VALUE, e);
        }
    }

    public boolean isSupportedPlantCategory(PlantCategory plantCategory) {
        return auctionPlantCategories.contains(plantCategory);
    }

    public boolean isNotSupportedPlantCategory(PlantCategory plantCategory) {
        return !isSupportedPlantCategory(plantCategory);
    }

    public String getFullName() {
        return String.format(FULL_NAME_FORMAT, name, city);
    }
}
