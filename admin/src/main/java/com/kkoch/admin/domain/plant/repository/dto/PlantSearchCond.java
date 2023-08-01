package com.kkoch.admin.domain.plant.repository.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PlantSearchCond {

    String code;

    String type;

    String name;

    @Builder
    private PlantSearchCond(String code, String type, String name) {
        this.code = code;
        this.type = type;
        this.name = name;
    }
}
