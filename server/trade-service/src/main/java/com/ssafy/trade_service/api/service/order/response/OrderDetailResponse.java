package com.ssafy.trade_service.api.service.order.response;

import com.ssafy.trade_service.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderDetailResponse {

    private Long id;
    private OrderStatus orderStatus;
    private int totalPrice;
    private Boolean isPickUp;
    private LocalDateTime pickUpDateTime;
    private List<BidResult> bidResults;

    @Builder
    private OrderDetailResponse(Long id, OrderStatus orderStatus, int totalPrice, Boolean isPickUp, LocalDateTime pickUpDateTime, List<BidResult> bidResults) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.isPickUp = isPickUp;
        this.pickUpDateTime = pickUpDateTime;
        this.bidResults = bidResults;
    }

    private static class BidResult {
        private Long id;
        private Variety variety;
        private int bidPrice;
        private LocalDateTime bidDateTime;

        @Builder
        private BidResult(Long id, Variety variety, int bidPrice, LocalDateTime bidDateTime) {
            this.id = id;
            this.variety = variety;
            this.bidPrice = bidPrice;
            this.bidDateTime = bidDateTime;
        }

        private static class Variety {
            private String plantGrade;
            private int plantCount;
            private String region;
            private String shipper;

            @Builder
            public Variety(String plantGrade, int plantCount, String region, String shipper) {
                this.plantGrade = plantGrade;
                this.plantCount = plantCount;
                this.region = region;
                this.shipper = shipper;
            }

            private static class VarietyInfo {
                private String varietyCode;
                private String plantCategory;
                private String itemName;
                private String varietyName;

                @Builder
                private VarietyInfo(String varietyCode, String plantCategory, String itemName, String varietyName) {
                    this.varietyCode = varietyCode;
                    this.plantCategory = plantCategory;
                    this.itemName = itemName;
                    this.varietyName = varietyName;
                }
            }
        }
    }
}
