package com.kkoch.user.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlantResponse {

    private Integer plantId;
    private String plantType;
    private String plantName;

    @Builder
    private PlantResponse(Integer plantId, String plantType, String plantName) {
        this.plantId = plantId;
        this.plantType = plantType;
        this.plantName = plantName;
    }
}
