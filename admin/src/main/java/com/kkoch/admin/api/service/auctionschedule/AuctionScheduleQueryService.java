package com.kkoch.admin.api.service.auctionschedule;

import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleQueryRepository;
import com.kkoch.admin.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import com.kkoch.admin.domain.auctionschedule.repository.response.OpenedAuctionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionScheduleQueryService {

    private final AuctionScheduleQueryRepository auctionScheduleQueryRepository;

    public PageResponse<AuctionScheduleResponse> searchAuctionSchedules(Pageable pageable) {
        int total = auctionScheduleQueryRepository.countByCond();

        List<Integer> auctionScheduleIds = auctionScheduleQueryRepository.findAllIdByCond(pageable);
        if (CollectionUtils.isEmpty(auctionScheduleIds)) {
            return PageResponse.empty(pageable, total);
        }

        List<AuctionScheduleResponse> content = auctionScheduleQueryRepository.findAllByIdIn(auctionScheduleIds);

        return PageResponse.create(content, pageable, total);
    }

    public OpenedAuctionResponse searchOpenedAuction() {
        return auctionScheduleQueryRepository.findByRoomStatusIsOpen()
            .orElse(null);
    }
}
