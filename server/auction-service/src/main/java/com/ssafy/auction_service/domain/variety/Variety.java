package com.ssafy.auction_service.domain.variety;

import com.ssafy.auction_service.domain.BaseEntity;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private PlantCategory plantCategory;

    @Column(nullable = false, updatable = false, length = 20)
    private String itemName;

    @Column(nullable = false, length = 20)
    private String varietyName;

    @Builder
    private Variety(boolean isDeleted, Long createdBy, Long lastModifiedBy, String code, PlantCategory plantCategory, String itemName, String varietyName) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.code = code;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }

    public static Variety of(boolean isDeleted, Long createdBy, Long lastModifiedBy, String code, PlantCategory plantCategory, String itemName, String varietyName) {
        return new Variety(isDeleted, createdBy, lastModifiedBy, code, plantCategory, itemName, varietyName);
    }

    public static Variety create(Long createdBy, String code, PlantCategory plantCategory, String itemName, String varietyName) {
        return of(false, createdBy, createdBy, code, plantCategory, itemName, varietyName);
    }

    public String getPlantCategoryDescription() {
        return plantCategory.getDescription();
    }

    public boolean plantCategoryEquals(PlantCategory plantCategory) {
        return this.plantCategory == plantCategory;
    }
}
