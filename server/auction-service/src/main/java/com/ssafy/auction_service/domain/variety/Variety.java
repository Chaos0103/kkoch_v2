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
    private Variety(boolean isDeleted, Long createdBy, Long lastModifiedBy, PlantCategory plantCategory, String code, String itemName, String varietyName) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.plantCategory = plantCategory;
        this.code = code;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }

    public static Variety of(boolean isDeleted, Long createdBy, Long lastModifiedBy, PlantCategory plantCategory, String code, String itemName, String varietyName) {
        return new Variety(isDeleted, createdBy, lastModifiedBy, plantCategory, code, itemName, varietyName);
    }
}
