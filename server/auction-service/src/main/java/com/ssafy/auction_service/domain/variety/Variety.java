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
    private Variety(boolean isDeleted, Long createdBy, Long lastModifiedBy, String code, VarietyInfo info) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.code = code;
        this.info = info;
    }

    public static Variety of(boolean isDeleted, Long createdBy, Long lastModifiedBy, String code, VarietyInfo info) {
        return new Variety(isDeleted, createdBy, lastModifiedBy, code, info);
    }

    public static Variety create(int equalPlantCategoryCount, PlantCategory plantCategory, String itemName, String varietyName) {
        String code = plantCategory.getNextCode(equalPlantCategoryCount);

        VarietyInfo info = VarietyInfo.of(plantCategory, itemName, varietyName);
        return of(false, null, null, code, info);
    }

    public void modifyVarietyName(String varietyName) {
        info = info.withVarietyName(varietyName);
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

    public VarietyInfo getModifiedInfo(String varietyName) {
        return VarietyInfo.of(info.getPlantCategory(), info.getItemName(), varietyName);
    }
}
