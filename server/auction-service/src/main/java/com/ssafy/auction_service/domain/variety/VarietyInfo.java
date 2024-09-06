package com.ssafy.auction_service.domain.variety;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class VarietyInfo {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 13)
    private final PlantCategory plantCategory;

    @Column(nullable = false, updatable = false, length = 20)
    private final String itemName;

    @Column(nullable = false, length = 20)
    private final String varietyName;

    @Builder
    private VarietyInfo(PlantCategory plantCategory, String itemName, String varietyName) {
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }

    public static VarietyInfo of(PlantCategory plantCategory, String itemName, String varietyName) {
        return new VarietyInfo(plantCategory, itemName, varietyName);
    }

    public VarietyInfo withVarietyName(String varietyName) {
        return of(plantCategory, itemName, varietyName);
    }

    public boolean plantCategoryEquals(PlantCategory plantCategory) {
        return this.plantCategory == plantCategory;
    }

    public String getKoreanPlantCategory() {
        return plantCategory.getDescription();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        VarietyInfo that = (VarietyInfo) object;
        return getPlantCategory() == that.getPlantCategory() && Objects.equals(getItemName(), that.getItemName()) && Objects.equals(getVarietyName(), that.getVarietyName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlantCategory(), getItemName(), getVarietyName());
    }
}
