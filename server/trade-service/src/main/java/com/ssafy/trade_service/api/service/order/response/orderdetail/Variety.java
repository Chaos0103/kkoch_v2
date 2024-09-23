package com.ssafy.trade_service.api.service.order.response.orderdetail;

public class Variety {
    private VarietyInfo varietyInfo;
    private String plantGrade;
    private int plantCount;
    private String region;
    private String shipper;

    private Variety(VarietyInfo varietyInfo, String plantGrade, int plantCount, String region, String shipper) {
        this.varietyInfo = varietyInfo;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.region = region;
        this.shipper = shipper;
    }

    public static Variety of(String varietyCode, String plantCategory, String itemName, String varietyName, String plantGrade, int plantCount, String region, String shipper) {
        VarietyInfo varietyInfo = VarietyInfo.of(varietyCode, plantCategory, itemName, varietyName);
        return new Variety(varietyInfo, plantGrade, plantCount, region, shipper);
    }

    private static class VarietyInfo {
        private String varietyCode;
        private String plantCategory;
        private String itemName;
        private String varietyName;

        private VarietyInfo(String varietyCode, String plantCategory, String itemName, String varietyName) {
            this.varietyCode = varietyCode;
            this.plantCategory = plantCategory;
            this.itemName = itemName;
            this.varietyName = varietyName;
        }

        public static VarietyInfo of(String varietyCode, String plantCategory, String itemName, String varietyName) {
            return new VarietyInfo(varietyCode, plantCategory, itemName, varietyName);
        }
    }
}
