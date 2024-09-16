package com.ssafy.trade_service.api.service.auctionreservation;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.client.MemberServiceClient;
import com.ssafy.trade_service.api.client.response.MemberIdResponse;
import com.ssafy.trade_service.api.service.auctionreservation.request.AuctionReservationModifyServiceRequest;
import com.ssafy.trade_service.api.service.auctionreservation.request.AuctionReservationCreateServiceRequest;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationCreateResponse;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationModifyResponse;
import com.ssafy.trade_service.common.exception.AppException;
import com.ssafy.trade_service.domain.auctionreservation.AuctionReservation;
import com.ssafy.trade_service.domain.auctionreservation.repository.AuctionReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ssafy.trade_service.domain.auctionreservation.repository.AuctionReservationRepository.NO_SUCH_AUCTION_RESERVATION;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionReservationService {

    private static final int MAXIMUM_NUMBER_OF_RESERVATIONS_PER_AUCTION = 10;
    private static final int MAXIMUM_NUMBER_OF_PLANT_COUNT_PER_AUCTION = 100;
    private static final String MAXIMUM_RESERVATION_EXCEEDED = "경매에 등록할 수 있는 최대 예약수를 초과했습니다.";
    private static final String MAXIMUM_PLANT_COUNT_EXCEEDED = "경매에 등록할 수 있는 최대 화훼단수를 초과했습니다.";

    private final AuctionReservationRepository auctionReservationRepository;
    private final MemberServiceClient memberServiceClient;

    public AuctionReservationCreateResponse createAuctionReservation(int auctionScheduleId, AuctionReservationCreateServiceRequest request) {
        Long memberId = getMemberId();

        List<Integer> content = auctionReservationRepository.findAllPlantCountByAuctionScheduleId(auctionScheduleId, memberId);
        PlantCounts plantCounts = request.getPlantCounts(content);

        if (plantCounts.isSizeMoreThan(MAXIMUM_NUMBER_OF_RESERVATIONS_PER_AUCTION)) {
            throw new AppException(MAXIMUM_RESERVATION_EXCEEDED);
        }

        if (plantCounts.isSumMoreThan(MAXIMUM_NUMBER_OF_PLANT_COUNT_PER_AUCTION)) {
            throw new AppException(MAXIMUM_PLANT_COUNT_EXCEEDED);
        }

        AuctionReservation auctionReservation = request.toEntity(memberId, auctionScheduleId);
        AuctionReservation savedAuctionReservation = auctionReservationRepository.save(auctionReservation);

        return AuctionReservationCreateResponse.of(savedAuctionReservation);
    }

    public AuctionReservationModifyResponse modifyAuctionReservation(long auctionReservationId, AuctionReservationModifyServiceRequest request) {
        AuctionReservation auctionReservation = auctionReservationRepository.findById(auctionReservationId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_AUCTION_RESERVATION));

        List<Integer> content = auctionReservationRepository.findAllPlantCountByAuctionScheduleId(auctionReservation.getAuctionScheduleId(), auctionReservation.getMemberId());
        PlantCounts plantCounts = request.getPlantCounts(content, auctionReservation.getPlantCount());

        if (plantCounts.isSumMoreThan(MAXIMUM_NUMBER_OF_PLANT_COUNT_PER_AUCTION)) {
            throw new AppException(MAXIMUM_PLANT_COUNT_EXCEEDED);
        }

        request.modify(auctionReservation);

        return AuctionReservationModifyResponse.of(auctionReservation);
    }

    private Long getMemberId() {
        ApiResponse<MemberIdResponse> response = memberServiceClient.searchMemberId();
        return response.getData().getMemberId();
    }
}
