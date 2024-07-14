package com.kkoch.admin.api.service.variety.request;

import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VarietyCreateServiceRequest {

    private final String plantCategory;
    private final String itemName;
    private final String varietyName;

    @Builder
    private VarietyCreateServiceRequest(String plantCategory, String itemName, String varietyName) {
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }

    public static VarietyCreateServiceRequest of(String plantCategory, String itemName, String varietyName) {
        return new VarietyCreateServiceRequest(plantCategory, itemName, varietyName);
    }

    public Variety toEntity(int count, Admin admin) {
        PlantCategory category = getPlantCategory();
        String code = category.getPrefixCode() + String.format("%04d", count);
        return Variety.create(code, category, itemName, varietyName, admin);
    }

    public PlantCategory getPlantCategory() {
        return PlantCategory.valueOf(plantCategory);
    }
}
