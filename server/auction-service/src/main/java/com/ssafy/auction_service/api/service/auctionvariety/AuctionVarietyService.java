package com.ssafy.auction_service.api.service.auctionvariety;

import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.client.response.MemberIdResponse;
import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.ssafy.auction_service.domain.auctionvariety.AuctionVariety;
import com.ssafy.auction_service.domain.auctionvariety.repository.AuctionVarietyRepository;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionVarietyService {

    private final AuctionVarietyRepository auctionVarietyRepository;
    private final VarietyRepository varietyRepository;
    private final AuctionScheduleRepository auctionScheduleRepository;
    private final MemberServiceClient memberServiceClient;

    public AuctionVarietyCreateResponse createAuctionVariety(String varietyCode, int auctionScheduleId, AuctionVarietyCreateServiceRequest request) {
        Variety variety = varietyRepository.findById(varietyCode)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 품종입니다."));

        AuctionSchedule auctionSchedule = auctionScheduleRepository.findById(auctionScheduleId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 경매 일정입니다."));

        if (auctionSchedule.isNotInitStatus()) {
            throw new AppException("경매 품종을 등록할 수 없습니다.");
        }

        if (auctionSchedule.isNotRegisteredVarietyBy(variety)) {
            throw new AppException("해당 경매에 등록할 수 없는 품종입니다.");
        }

        Long memberId = getMemberId();

        String listingNumber = generateListingNumberBy(auctionSchedule);

        AuctionVariety auctionVariety = request.toEntity(memberId, auctionSchedule, variety, listingNumber);
        AuctionVariety savedAuctionVariety = auctionVarietyRepository.save(auctionVariety);

        return AuctionVarietyCreateResponse.of(savedAuctionVariety);
    }

    private String generateListingNumberBy(AuctionSchedule auctionSchedule) {
        int count = auctionVarietyRepository.countByAuctionSchedule(auctionSchedule);
        return String.format("%05d", count + 1);
    }

    private Long getMemberId() {
        ApiResponse<MemberIdResponse> response = memberServiceClient.searchMemberId();
        return response.getData().getMemberId();
    }
}
