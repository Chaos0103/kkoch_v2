package com.kkoch.admin.domain.variety;

import com.kkoch.admin.domain.BaseEntity;
import com.kkoch.admin.domain.admin.Admin;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Variety extends BaseEntity {

    @Id
    @Column(name = "variety_code", nullable = false, updatable = false, columnDefinition = "char(8)", length = 8)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 13)
    private PlantCategory plantCategory;

    @Column(nullable = false, length = 20)
    private String itemName;

    @Column(nullable = false, length = 20)
    private String varietyName;

    @Builder
    private Variety(boolean isDeleted, int createdBy, int lastModifiedBy, String code, PlantCategory plantCategory, String itemName, String varietyName) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.code = code;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }

    public static Variety of(boolean isDeleted, int createdBy, int lastModifiedBy, String code, PlantCategory plantCategory, String itemName, String varietyName) {
        return new Variety(isDeleted, createdBy, lastModifiedBy, code, plantCategory, itemName, varietyName);
    }

    public static Variety create(String code, PlantCategory plantCategory, String itemName, String varietyName, Admin admin) {
        return of(false, admin.getId(), admin.getId(), code, plantCategory, itemName, varietyName);
    }
}
