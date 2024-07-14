package com.kkoch.admin.api.service.auctionschedule;

import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleQueryRepository;
import com.kkoch.admin.domain.auctionschedule.repository.response.OpenedAuctionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionScheduleQueryService {

    private final AuctionScheduleQueryRepository auctionScheduleQueryRepository;

    public OpenedAuctionResponse searchOpenedAuction() {
        return auctionScheduleQueryRepository.findByRoomStatusIsOpen()
            .orElse(null);
    }
}
