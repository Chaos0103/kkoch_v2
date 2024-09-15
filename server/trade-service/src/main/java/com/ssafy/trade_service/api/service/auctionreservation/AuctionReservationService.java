package com.ssafy.trade_service.api.service.auctionreservation;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.client.MemberServiceClient;
import com.ssafy.trade_service.api.client.response.MemberIdResponse;
import com.ssafy.trade_service.api.service.auctionreservation.request.AuctionReservationServiceRequest;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationResponse;
import com.ssafy.trade_service.common.exception.AppException;
import com.ssafy.trade_service.domain.auctionreservation.AuctionReservation;
import com.ssafy.trade_service.domain.auctionreservation.repository.AuctionReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionReservationService {

    private final AuctionReservationRepository auctionReservationRepository;
    private final MemberServiceClient memberServiceClient;

    public AuctionReservationResponse createAuctionReservation(int auctionScheduleId, AuctionReservationServiceRequest request) {
        Long memberId = getMemberId();

        List<Integer> plantCounts = auctionReservationRepository.findAllPlantCountByAuctionScheduleId(auctionScheduleId, memberId);
        if (plantCounts.size() >= 10) {
            throw new AppException("경매에 등록할 수 있는 최대 예약수를 초과했습니다.");
        }

        int sum = plantCounts.stream()
            .mapToInt(m -> m)
            .sum();
        if (sum >= 100) {
            throw new AppException("경매에 등록할 수 있는 최대 화훼단수를 초과했습니다.");
        }

        AuctionReservation auctionReservation = request.toEntity(memberId, auctionScheduleId);
        AuctionReservation savedAuctionReservation = auctionReservationRepository.save(auctionReservation);

        return AuctionReservationResponse.of(savedAuctionReservation);
    }

    private Long getMemberId() {
        ApiResponse<MemberIdResponse> response = memberServiceClient.searchMemberId();
        return response.getData().getMemberId();
    }
}
