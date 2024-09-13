package com.ssafy.auction_service.api.service.variety;

import com.ssafy.auction_service.api.PageResponse;
import com.ssafy.auction_service.domain.variety.repository.VarietyQueryRepository;
import com.ssafy.auction_service.domain.variety.repository.cond.VarietySearchCond;
import com.ssafy.auction_service.domain.variety.repository.response.VarietyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VarietyQueryService {

    private final VarietyQueryRepository varietyQueryRepository;

    public PageResponse<VarietyResponse> searchVarieties(VarietySearchCond cond, int pageNumber) {
        return null;
    }

}
