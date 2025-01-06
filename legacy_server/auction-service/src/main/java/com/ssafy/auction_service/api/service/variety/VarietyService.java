package com.ssafy.auction_service.api.service.variety;

import com.ssafy.auction_service.api.service.variety.request.VarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.variety.response.VarietyCreateResponse;
import com.ssafy.auction_service.api.service.variety.response.VarietyModifyResponse;
import com.ssafy.auction_service.api.service.variety.response.VarietyRemoveResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.ssafy.auction_service.domain.variety.repository.VarietyRepository.NO_SUCH_VARIETY;

@Service
@Transactional
@RequiredArgsConstructor
public class VarietyService {

    private static final String DUPLICATED_VARIETY = "이미 등록된 품종입니다.";

    private final VarietyRepository varietyRepository;

    public VarietyCreateResponse createVariety(VarietyCreateServiceRequest request) {
        int count = varietyRepository.countByInfoPlantCategory(request.getPlantCategory());

        Variety variety = request.toEntity(count);
        VarietyInfo info = variety.getInfo();
        checkDuplicateVariety(info);

        Variety savedVariety = varietyRepository.save(variety);

        return VarietyCreateResponse.of(savedVariety);
    }

    public VarietyModifyResponse modifyVariety(String varietyCode, String varietyName) {
        Variety variety = findVarietyByCode(varietyCode);

        VarietyInfo info = variety.getInfoWithVarietyName(varietyName);
        checkDuplicateVariety(info);

        variety.modifyVarietyName(varietyName);

        return VarietyModifyResponse.of(variety);
    }

    public VarietyRemoveResponse removeVariety(String varietyCode) {
        Variety variety = findVarietyByCode(varietyCode);

        variety.remove();

        return VarietyRemoveResponse.of(variety);
    }

    private void checkDuplicateVariety(VarietyInfo info) {
        varietyRepository.findCodeByVariety(info)
            .ifPresent(varietyCode -> {
                throw new AppException(DUPLICATED_VARIETY);
            });
    }

    private Variety findVarietyByCode(String code) {
        return varietyRepository.findById(code)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_VARIETY));
    }
}
