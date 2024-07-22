package com.kkoch.admin.api.service.bidresult;

import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.domain.bidresult.repository.BidResultQueryRepository;
import com.kkoch.admin.domain.bidresult.repository.dto.BidResultSearchCond;
import com.kkoch.admin.domain.bidresult.repository.response.BidResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidResultQueryService {

    private final BidResultQueryRepository bidResultQueryRepository;

    public PageResponse<BidResultResponse> searchBidResultsBy(BidResultSearchCond cond, Pageable pageable) {
        int total = bidResultQueryRepository.countByCond(cond);

        List<Long> bidResultIds = bidResultQueryRepository.findAllIdByCond(cond, pageable);
        if (CollectionUtils.isEmpty(bidResultIds)) {
            return PageResponse.empty(pageable, total);
        }

        List<BidResultResponse> content = bidResultQueryRepository.findAllByIdIn(bidResultIds);

        return PageResponse.create(content, pageable, total);
    }
}
