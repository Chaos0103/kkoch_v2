package com.kkoch.admin.api.service.auctionvariety;

import com.kkoch.admin.domain.auctionvariety.repository.AuctionVarietyQueryRepository;
import com.kkoch.admin.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionVarietyQueryService {

    private final AuctionVarietyQueryRepository auctionVarietyQueryRepository;

    public List<AuctionVarietyResponse> searchAuctionVarietiesBy(int auctionScheduleId) {
        return auctionVarietyQueryRepository.findByAuctionScheduleId(auctionScheduleId);
    }
}
