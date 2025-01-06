package com.ssafy.auction_service.domain.auctionschedule;

import com.ssafy.auction_service.common.exception.NotSupportedException;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JointMarketTest {

    @DisplayName("입력된 문자열을 JointMarket 타입으로 만든다.")
    @Test
    void of() {
        //given
        String str = "YANGJAE";

        //when
        JointMarket jointMarket = JointMarket.of(str);

        //then
        assertThat(jointMarket).isEqualByComparingTo(JointMarket.YANGJAE);
    }

    @DisplayName("입력된 문자열의 공판장을 지원하지 않으면 예외가 발생한다.")
    @Test
    void notSupportedJointMarket() {
        //given
        String str = "SEOUL";

        //when //then
        assertThatThrownBy(() -> JointMarket.of(str))
            .isInstanceOf(NotSupportedException.class)
            .hasMessage("지원하지 않는 공판장입니다.");
    }

    @DisplayName("공판장에서 지원하는 화훼부류 여부를 확인한다.")
    @CsvSource({"CUT_FLOWERS,true", "ORCHID,true", "FOLIAGE,true", "VERNALIZATION,false"})
    @ParameterizedTest
    void isSupportedPlantCategory(PlantCategory plantCategory, boolean expected) {
        //given
        JointMarket jointMarket = JointMarket.YANGJAE;

        //when
        boolean result = jointMarket.isSupportedPlantCategory(plantCategory);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("공판장에서 지원하지 않는 화훼부류 여부를 확인한다.")
    @CsvSource({"CUT_FLOWERS,false", "ORCHID,false", "FOLIAGE,false", "VERNALIZATION,true"})
    @ParameterizedTest
    void isNotSupportedPlantCategory(PlantCategory plantCategory, boolean expected) {
        //given
        JointMarket jointMarket = JointMarket.YANGJAE;

        //when
        boolean result = jointMarket.isNotSupportedPlantCategory(plantCategory);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("공판장 풀네임을 반환한다.")
    @Test
    void getFullName() {
        //given
        JointMarket jointMarket = JointMarket.YANGJAE;

        //when
        String fullName = jointMarket.getFullName();

        //then
        assertThat(fullName).isEqualTo("aT화훼공판장(양재동)");
    }
}