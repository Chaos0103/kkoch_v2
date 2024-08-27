package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionScheduleService {

    private final AuctionScheduleRepository auctionScheduleRepository;
    private final MemberServiceClient memberServiceClient;

    public AuctionScheduleCreateResponse createAuctionSchedule(AuctionScheduleCreateServiceRequest request, LocalDateTime current) {
        return null;
    }
}
