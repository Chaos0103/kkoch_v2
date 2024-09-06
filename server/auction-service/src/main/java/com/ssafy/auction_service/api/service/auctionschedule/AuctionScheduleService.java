package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.client.response.MemberIdResponse;
import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionStatusModifyResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionScheduleService {

    private final AuctionScheduleRepository auctionScheduleRepository;
    private final MemberServiceClient memberServiceClient;

    public AuctionScheduleCreateResponse createAuctionSchedule(AuctionScheduleCreateServiceRequest request, LocalDateTime current) {
        Optional<Integer> auctionScheduleId = auctionScheduleRepository.findIdByAuction(request.getPlantCategory(), request.getJointMarket(), request.getAuctionStartDateTime());
        if (auctionScheduleId.isPresent()) {
            throw new AppException("이미 등록된 경매 일정이 있습니다.");
        }

        Long memberId = getMemberId();

        AuctionSchedule auctionSchedule = request.toEntity(memberId, current);
        AuctionSchedule savedAuctionSchedule = auctionScheduleRepository.save(auctionSchedule);

        return AuctionScheduleCreateResponse.of(savedAuctionSchedule);
    }

    public AuctionStatusModifyResponse modifyAuctionStatusToReady(int auctionScheduleId, LocalDateTime current) {
        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        if (auctionSchedule.isProgress()) {
            throw new AppException("진행중인 경매입니다.");
        }

        if (auctionSchedule.isComplete()) {
            throw new AppException("완료된 경매입니다.");
        }

        Long memberId = getMemberId();

        auctionSchedule.ready(memberId);

        return AuctionStatusModifyResponse.of(auctionSchedule, current);
    }

    public AuctionStatusModifyResponse modifyAuctionStatusToProgress(int auctionScheduleId, LocalDateTime current) {
        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        if (auctionSchedule.isInit()) {
            throw new AppException("준비된 경매가 아닙니다.");
        }

        if (auctionSchedule.isComplete()) {
            throw new AppException("완료된 경매입니다.");
        }

        Long memberId = getMemberId();

        auctionSchedule.progress(memberId);

        return AuctionStatusModifyResponse.of(auctionSchedule, current);
    }

    public AuctionStatusModifyResponse modifyAuctionStatusToComplete(int auctionScheduleId, LocalDateTime current) {
        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        if (auctionSchedule.isInit()) {
            throw new AppException("진행중인 경매가 아닙니다.");
        }

        if (auctionSchedule.isReady()) {
            throw new AppException("진행중인 경매가 아닙니다.");
        }

        Long memberId = getMemberId();

        auctionSchedule.complete(memberId);

        return AuctionStatusModifyResponse.of(auctionSchedule, current);
    }

    private AuctionSchedule findAuctionScheduleById(int auctionScheduleId) {
        return auctionScheduleRepository.findById(auctionScheduleId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 경매 일정입니다."));
    }

    private Long getMemberId() {
        ApiResponse<MemberIdResponse> response = memberServiceClient.searchMemberId();
        return response.getData().getMemberId();
    }
}
