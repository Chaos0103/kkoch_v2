package com.ssafy.trade_service.api.service.auctionreservation;

import com.ssafy.trade_service.api.service.auctionreservation.request.AuctionReservationServiceRequest;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationResponse;
import com.ssafy.trade_service.domain.auctionreservation.repository.AuctionReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionReservationService {

    private final AuctionReservationRepository auctionReservationRepository;

    public AuctionReservationResponse createAuctionReservation(int auctionScheduleId, AuctionReservationServiceRequest request) {
        return null;
    }
}
