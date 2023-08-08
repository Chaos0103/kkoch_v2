package com.kkoch.admin.api.service.plant;

import com.kkoch.admin.api.controller.plant.response.PlantResponse;
import com.kkoch.admin.api.service.plant.dto.PlantSearchCond;
import com.kkoch.admin.domain.plant.repository.PlantQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PlantQueryService {

    private final PlantQueryRepository plantQueryRepository;

    public List<PlantResponse> getPlants() {
        return plantQueryRepository.getPlants().stream().map(PlantResponse::of).collect(Collectors.toList());
    }

    public Long getPlantId(String type, String name) {
        return plantQueryRepository.findPlantId(type, name);
    }
}
