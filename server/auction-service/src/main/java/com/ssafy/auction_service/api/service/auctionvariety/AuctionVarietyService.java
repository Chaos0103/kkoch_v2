package com.ssafy.auction_service.api.service.auctionvariety;

import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyModifyServiceRequest;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyModifyResponse;
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

    public AuctionVarietyCreateResponse createAuctionVariety(String varietyCode, int auctionScheduleId, AuctionVarietyCreateServiceRequest request) {
        Variety variety = findVarietyByCode(varietyCode);

        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        if (auctionSchedule.isNotInit()) {
            throw new AppException("경매 품종을 등록할 수 없습니다.");
        }

        if (auctionSchedule.isNotRegisteredVarietyBy(variety)) {
            throw new AppException("해당 경매에 등록할 수 없는 품종입니다.");
        }

        String listingNumber = generateListingNumberBy(auctionSchedule);

        AuctionVariety auctionVariety = request.toEntity(auctionSchedule, variety, listingNumber);
        AuctionVariety savedAuctionVariety = auctionVarietyRepository.save(auctionVariety);

        return AuctionVarietyCreateResponse.of(savedAuctionVariety);
    }

    public AuctionVarietyModifyResponse modifyAuctionVariety(long auctionVarietyId, AuctionVarietyModifyServiceRequest request) {
        AuctionVariety auctionVariety = auctionVarietyRepository.findById(auctionVarietyId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 경매 품종입니다."));

        if (auctionVariety.getAuctionSchedule().isNotInit()) {
            throw new AppException("경매 품종을 수정할 수 없습니다.");
        }

        request.modify(auctionVariety);

        return AuctionVarietyModifyResponse.of(auctionVariety);
    }

    private String generateListingNumberBy(AuctionSchedule auctionSchedule) {
        int count = auctionVarietyRepository.countByAuctionSchedule(auctionSchedule);
        return String.format("%05d", count + 1);
    }


    private Variety findVarietyByCode(String varietyCode) {
        return varietyRepository.findById(varietyCode)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 품종입니다."));
    }

    private AuctionSchedule findAuctionScheduleById(int auctionScheduleId) {
        return auctionScheduleRepository.findById(auctionScheduleId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 경매 일정입니다."));
    }
}
