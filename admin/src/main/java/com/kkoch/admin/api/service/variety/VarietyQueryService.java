package com.kkoch.admin.api.service.variety;

import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.repository.VarietyQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VarietyQueryService {

    private final VarietyQueryRepository varietyQueryRepository;

    public List<String> searchItemNames(String plantCategory) {
        PlantCategory category = PlantCategory.valueOf(plantCategory);

        return varietyQueryRepository.findAllItemNameByPlantCategory(category);
    }

    public List<String> searchVarietyNames(String itemName) {
        return varietyQueryRepository.findAllVarietyNameByItemName(itemName);
    }
}
