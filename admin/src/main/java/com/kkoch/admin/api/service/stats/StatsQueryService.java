package com.kkoch.admin.api.service.stats;

import com.kkoch.admin.api.ListResponse;
import com.kkoch.admin.domain.stats.repository.StatsQueryRepository;
import com.kkoch.admin.domain.stats.repository.dto.StatsSearchCond;
import com.kkoch.admin.domain.stats.repository.response.StatsResponse;
import com.kkoch.admin.domain.variety.Variety;
import com.kkoch.admin.domain.variety.repository.VarietyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatsQueryService {

    private final StatsQueryRepository statsQueryRepository;
    private final VarietyRepository varietyRepository;

    public ListResponse<StatsResponse> searchStats(StatsSearchCond cond, LocalDate currentDate) {
        Variety variety = varietyRepository.findById(cond.getVarietyCode())
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 품종입니다."));

        List<StatsResponse> content = statsQueryRepository.findAllByCond(cond, currentDate, variety.getItemName(), variety.getVarietyName());

        return ListResponse.of(content);
    }
}
