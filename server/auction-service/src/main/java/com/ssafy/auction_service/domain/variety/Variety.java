package com.ssafy.auction_service.domain.variety;

import com.ssafy.auction_service.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Variety extends BaseEntity {

    @Id
    @Column(name = "variety_code")
    private String code;

    @Embedded
    private VarietyInfo info;

    @Builder
    private Variety(boolean isDeleted, String code, VarietyInfo info) {
        super(isDeleted);
        this.code = code;
        this.info = info;
    }

    public static Variety of(boolean isDeleted, String code, VarietyInfo info) {
        return new Variety(isDeleted, code, info);
    }

    public static Variety create(int equalPlantCategoryCount, PlantCategory plantCategory, String itemName, String varietyName) {
        String generatedCode = plantCategory.getNextCode(equalPlantCategoryCount);

        VarietyInfo info = VarietyInfo.of(plantCategory, itemName, varietyName);
        return of(false, generatedCode, info);
    }

    public void modifyVarietyName(String varietyName) {
        info = info.withVarietyName(varietyName);
    }

    @Override
    public void remove() {
        super.remove();
    }

    public boolean plantCategoryEquals(PlantCategory plantCategory) {
        return info.plantCategoryEquals(plantCategory);
    }

    public String getKoreanPlantCategory() {
        return info.getKoreanPlantCategory();
    }

    public String getItemName() {
        return info.getItemName();
    }

    public String getVarietyName() {
        return info.getVarietyName();
    }

    public VarietyInfo getInfoWithVarietyName(String varietyName) {
        return info.withVarietyName(varietyName);
    }
}
