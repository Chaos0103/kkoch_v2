package com.ssafy.auction_service.api.service.variety;

import com.ssafy.auction_service.api.service.variety.request.VarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.variety.response.VarietyCreateResponse;
import com.ssafy.auction_service.api.service.variety.response.VarietyModifyResponse;
import com.ssafy.auction_service.api.service.variety.response.VarietyRemoveResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VarietyService {

    private final VarietyRepository varietyRepository;

    public VarietyCreateResponse createVariety(VarietyCreateServiceRequest request) {
        Optional<String> varietyCode = varietyRepository.findCodeByVariety(request.getVarietyInfo());
        if (varietyCode.isPresent()) {
            throw new AppException("이미 등록된 품종입니다.");
        }

        int count = varietyRepository.countByInfoPlantCategory(request.getPlantCategory());

        Variety variety = request.toEntity(count);
        Variety savedVariety = varietyRepository.save(variety);

        return VarietyCreateResponse.of(savedVariety);
    }

    public VarietyModifyResponse modifyVariety(String varietyCode, String varietyName, LocalDateTime current) {
        Variety variety = findVarietyByCode(varietyCode);

        Optional<String> code = varietyRepository.findCodeByVariety(variety.getModifiedInfo(varietyName));
        if (code.isPresent()) {
            throw new AppException("이미 등록된 품종입니다.");
        }

        variety.modifyVarietyName(varietyName);

        return VarietyModifyResponse.of(variety, current);
    }

    public VarietyRemoveResponse removeVariety(String varietyCode) {
        Variety variety = findVarietyByCode(varietyCode);

        variety.remove();

        return VarietyRemoveResponse.of(variety);
    }

    private Variety findVarietyByCode(String code) {
        return varietyRepository.findById(code)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 품종입니다."));
    }
}
