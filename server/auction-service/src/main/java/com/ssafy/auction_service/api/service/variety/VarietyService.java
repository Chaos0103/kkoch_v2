package com.ssafy.auction_service.api.service.variety;

import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.client.response.MemberIdResponse;
import com.ssafy.auction_service.api.service.variety.request.VarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.variety.response.VarietyCreateResponse;
import com.ssafy.auction_service.api.service.variety.response.VarietyModifyResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.variety.PlantCategory;
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
    private final MemberServiceClient memberServiceClient;

    public VarietyCreateResponse createVariety(VarietyCreateServiceRequest request) {
        Optional<String> varietyCode = varietyRepository.findCodeByVariety(request.getPlantCategory(), request.getItemName(), request.getVarietyName());
        if (varietyCode.isPresent()) {
            throw new AppException("이미 등록된 품종입니다.");
        }

        Long memberId = getMemberId();

        String nextCode = generateNextCode(request.getPlantCategory());
        Variety variety = request.toEntity(memberId, nextCode);
        Variety savedVariety = varietyRepository.save(variety);

        return VarietyCreateResponse.of(savedVariety);
    }

    public VarietyModifyResponse modifyVariety(String varietyCode, String varietyName, LocalDateTime current) {
        Variety variety = findVarietyByCode(varietyCode);

        Optional<String> code = varietyRepository.findCodeByVariety(variety.getPlantCategory(), variety.getItemName(), varietyName);
        if (code.isPresent()) {
            throw new AppException("이미 등록된 품종입니다.");
        }

        Long memberId = getMemberId();
        variety.modifyVarietyName(memberId, varietyName);

        return VarietyModifyResponse.of(variety, current);
    }

    private Variety findVarietyByCode(String code) {
        return varietyRepository.findById(code)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 품종입니다."));
    }

    private Long getMemberId() {
        ApiResponse<MemberIdResponse> response = memberServiceClient.searchMemberId();
        return response.getData().getMemberId();
    }

    private String generateNextCode(PlantCategory plantCategory) {
        int count = varietyRepository.countByPlantCategory(plantCategory);
        return String.format("%s%04d", plantCategory.getPrefix(), count + 1);
    }
}
