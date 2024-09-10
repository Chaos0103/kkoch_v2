package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleModifyServiceRequest;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleModifyResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleRemoveResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionStatusModifyResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository.NO_SUCH_AUCTION_SCHEDULE;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionScheduleService {

    private static final String NOT_MODIFIABLE_AUCTION_SCHEDULE = "더이상 경매 일정을 수정할 수 없습니다.";
    private static final String NOT_REMOVABLE_AUCTION_SCHEDULE = "경매 일정을 삭제할 수 없습니다.";
    private static final String IS_NOT_READY_AUCTION_SCHEDULE = "준비된 경매가 아닙니다.";
    private static final String IS_PROGRESS_AUCTION_SCHEDULE = "진행중인 경매입니다.";
    private static final String IS_NOT_PROGRESS_AUCTION_SCHEDULE = "진행중인 경매가 아닙니다.";
    private static final String IS_COMPLETE_AUCTION_SCHEDULE = "완료된 경매입니다.";
    private static final String DUPLICATE_AUCTION_SCHEDULE = "이미 등록된 경매 일정이 있습니다.";

    private final AuctionScheduleRepository auctionScheduleRepository;

    public AuctionScheduleCreateResponse createAuctionSchedule(AuctionScheduleCreateServiceRequest request, LocalDateTime current) {
        checkDuplicateAuctionSchedule(request.getAuctionInfo());

        AuctionSchedule auctionSchedule = request.toEntity(current);
        AuctionSchedule savedAuctionSchedule = auctionScheduleRepository.save(auctionSchedule);

        return AuctionScheduleCreateResponse.of(savedAuctionSchedule);
    }

    public AuctionScheduleModifyResponse modifyAuctionSchedule(int auctionScheduleId, AuctionScheduleModifyServiceRequest request, LocalDateTime current) {
        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        checkDuplicateAuctionSchedule(request.getAuctionInfo(auctionSchedule));

        if (auctionSchedule.isNotModifiable()) {
            throw new AppException(NOT_MODIFIABLE_AUCTION_SCHEDULE);
        }

        request.modify(auctionSchedule);

        return AuctionScheduleModifyResponse.of(auctionSchedule, current);
    }

    public AuctionStatusModifyResponse modifyAuctionStatusToReady(int auctionScheduleId, LocalDateTime current) {
        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        if (auctionSchedule.isProgress()) {
            throw new AppException(IS_PROGRESS_AUCTION_SCHEDULE);
        }

        if (auctionSchedule.isComplete()) {
            throw new AppException(IS_COMPLETE_AUCTION_SCHEDULE);
        }

        auctionSchedule.ready();

        return AuctionStatusModifyResponse.of(auctionSchedule, current);
    }

    public AuctionStatusModifyResponse modifyAuctionStatusToProgress(int auctionScheduleId, LocalDateTime current) {
        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        if (auctionSchedule.isInit()) {
            throw new AppException(IS_NOT_READY_AUCTION_SCHEDULE);
        }

        if (auctionSchedule.isComplete()) {
            throw new AppException(IS_COMPLETE_AUCTION_SCHEDULE);
        }

        auctionSchedule.progress();

        return AuctionStatusModifyResponse.of(auctionSchedule, current);
    }

    public AuctionStatusModifyResponse modifyAuctionStatusToComplete(int auctionScheduleId, LocalDateTime current) {
        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        if (auctionSchedule.isInit()) {
            throw new AppException(IS_NOT_PROGRESS_AUCTION_SCHEDULE);
        }

        if (auctionSchedule.isReady()) {
            throw new AppException(IS_NOT_PROGRESS_AUCTION_SCHEDULE);
        }

        auctionSchedule.complete();

        return AuctionStatusModifyResponse.of(auctionSchedule, current);
    }

    public AuctionScheduleRemoveResponse removeAuctionSchedule(int auctionScheduleId, LocalDateTime current) {
        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        if (auctionSchedule.isNotRemovable()) {
            throw new AppException(NOT_REMOVABLE_AUCTION_SCHEDULE);
        }

        auctionSchedule.remove();

        return AuctionScheduleRemoveResponse.of(auctionSchedule, current);
    }

    private AuctionSchedule findAuctionScheduleById(int auctionScheduleId) {
        return auctionScheduleRepository.findById(auctionScheduleId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_AUCTION_SCHEDULE));
    }

    private void checkDuplicateAuctionSchedule(AuctionInfo request) {
        Optional<Integer> auctionScheduleId = auctionScheduleRepository.findIdByAuction(request);
        if (auctionScheduleId.isPresent()) {
            throw new AppException(DUPLICATE_AUCTION_SCHEDULE);
        }
    }
}
