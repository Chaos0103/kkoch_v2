package com.ssafy.auction_service.api.service.variety;

import com.ssafy.auction_service.api.ListResponse;
import com.ssafy.auction_service.api.PageResponse;
import com.ssafy.auction_service.common.util.PageUtils;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.repository.VarietyQueryRepository;
import com.ssafy.auction_service.domain.variety.repository.cond.VarietySearchCond;
import com.ssafy.auction_service.domain.variety.repository.response.ItemNameResponse;
import com.ssafy.auction_service.domain.variety.repository.response.VarietyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VarietyQueryService {

    private final VarietyQueryRepository varietyQueryRepository;

    public PageResponse<VarietyResponse> searchVarieties(VarietySearchCond cond, int pageNumber) {
        Pageable pageable = PageUtils.of(pageNumber);

        List<VarietyResponse> content = varietyQueryRepository.findAllByCond(cond, pageable);

        int total = varietyQueryRepository.countByCond(cond);

        return PageResponse.create(content, pageable, total);
    }

    public ListResponse<ItemNameResponse> searchItemNames(PlantCategory plantCategory) {
        return null;
    }
}
