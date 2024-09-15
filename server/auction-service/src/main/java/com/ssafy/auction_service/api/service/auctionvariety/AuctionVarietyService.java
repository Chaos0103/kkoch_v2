package com.ssafy.auction_service.api.service.auctionvariety;

import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyModifyServiceRequest;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyModifyResponse;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyRemoveResponse;
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

import static com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository.NO_SUCH_AUCTION_SCHEDULE;
import static com.ssafy.auction_service.domain.auctionvariety.repository.AuctionVarietyRepository.NO_SUCH_AUCTION_VARIETY;
import static com.ssafy.auction_service.domain.variety.repository.VarietyRepository.NO_SUCH_VARIETY;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionVarietyService {

    private static final String LISTING_NUMBER_FORMAT = "%05d";
    private static final String UNABLE_TO_REGISTER_AUCTION_VARIETY = "경매 품종을 등록할 수 없습니다.";
    private static final String UNABLE_TO_MODIFY_AUCTION_VARIETY = "경매 품종을 수정할 수 없습니다.";
    private static final String UNABLE_TO_REMOVE_AUCTION_VARIETY = "경매 품종을 삭제할 수 없습니다.";
    private static final String UNABLE_TO_REGISTER_VARIETY_FOR_AUCTION_SCHEDULE = "해당 경매에 등록할 수 없는 품종입니다.";

    private final AuctionVarietyRepository auctionVarietyRepository;
    private final VarietyRepository varietyRepository;
    private final AuctionScheduleRepository auctionScheduleRepository;

    public AuctionVarietyCreateResponse createAuctionVariety(String varietyCode, int auctionScheduleId, AuctionVarietyCreateServiceRequest request) {
        Variety variety = findVarietyByCode(varietyCode);

        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        if (auctionSchedule.isNotInit()) {
            throw new AppException(UNABLE_TO_REGISTER_AUCTION_VARIETY);
        }

        if (auctionSchedule.cannotRegister(variety)) {
            throw new AppException(UNABLE_TO_REGISTER_VARIETY_FOR_AUCTION_SCHEDULE);
        }

        String listingNumber = generateListingNumberBy(auctionSchedule);

        AuctionVariety auctionVariety = request.toEntity(auctionSchedule, variety, listingNumber);
        AuctionVariety savedAuctionVariety = auctionVarietyRepository.save(auctionVariety);

        return AuctionVarietyCreateResponse.of(savedAuctionVariety);
    }

    public AuctionVarietyModifyResponse modifyAuctionVariety(long auctionVarietyId, AuctionVarietyModifyServiceRequest request) {
        AuctionVariety auctionVariety = findAuctionVarietyById(auctionVarietyId);

        if (auctionVariety.isNotModifiable()) {
            throw new AppException(UNABLE_TO_MODIFY_AUCTION_VARIETY);
        }

        request.modify(auctionVariety);

        return AuctionVarietyModifyResponse.of(auctionVariety);
    }

    public AuctionVarietyRemoveResponse removeAuctionVariety(long auctionVarietyId) {
        AuctionVariety auctionVariety = findAuctionVarietyById(auctionVarietyId);

        if (auctionVariety.isNotRemovable()) {
            throw new AppException(UNABLE_TO_REMOVE_AUCTION_VARIETY);
        }

        auctionVariety.remove();

        return AuctionVarietyRemoveResponse.of(auctionVariety);
    }

    private String generateListingNumberBy(AuctionSchedule auctionSchedule) {
        int count = auctionVarietyRepository.countByAuctionSchedule(auctionSchedule);
        return String.format(LISTING_NUMBER_FORMAT, count + 1);
    }

    private Variety findVarietyByCode(String varietyCode) {
        return varietyRepository.findById(varietyCode)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_VARIETY));
    }

    private AuctionSchedule findAuctionScheduleById(int auctionScheduleId) {
        return auctionScheduleRepository.findById(auctionScheduleId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_AUCTION_SCHEDULE));
    }

    private AuctionVariety findAuctionVarietyById(long auctionVarietyId) {
        return auctionVarietyRepository.findById(auctionVarietyId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_AUCTION_VARIETY));
    }
}
