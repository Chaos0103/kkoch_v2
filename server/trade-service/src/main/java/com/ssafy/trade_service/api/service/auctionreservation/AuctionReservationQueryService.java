package com.ssafy.trade_service.api.service.auctionreservation;

import com.ssafy.trade_service.api.ListResponse;
import com.ssafy.trade_service.api.client.MemberServiceClient;
import com.ssafy.trade_service.domain.auctionreservation.repository.AuctionReservationQueryRepository;
import com.ssafy.trade_service.domain.auctionreservation.repository.response.AuctionReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionReservationQueryService {

    private final AuctionReservationQueryRepository auctionReservationQueryRepository;
    private final MemberServiceClient memberServiceClient;

    public ListResponse<AuctionReservationResponse> searchAuctionReservations(int auctionScheduleId) {
        return null;
    }
}
