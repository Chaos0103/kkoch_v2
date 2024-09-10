package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.client.response.MemberIdResponse;
import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleModifyServiceRequest;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleModifyResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleRemoveResponse;
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
        Optional<Integer> auctionScheduleId = auctionScheduleRepository.findIdByAuction(request.getAuctionInfo());
        if (auctionScheduleId.isPresent()) {
            throw new AppException("이미 등록된 경매 일정이 있습니다.");
        }

        Long memberId = getMemberId();

        AuctionSchedule auctionSchedule = request.toEntity(memberId, current);
        AuctionSchedule savedAuctionSchedule = auctionScheduleRepository.save(auctionSchedule);

        return AuctionScheduleCreateResponse.of(savedAuctionSchedule);
    }

    public AuctionScheduleModifyResponse modifyAuctionSchedule(int auctionScheduleId, AuctionScheduleModifyServiceRequest request, LocalDateTime current) {
        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        Optional<Integer> findAuctionScheduleId = auctionScheduleRepository.findIdByAuction(request.getAuctionInfo(auctionSchedule));
        if (findAuctionScheduleId.isPresent()) {
            throw new AppException("이미 등록된 경매 일정이 있습니다.");
        }

        if (auctionSchedule.isNotModifiable()) {
            throw new AppException("더이상 경매 일정을 수정할 수 없습니다.");
        }

        Long memberId = getMemberId();

        request.modify(auctionSchedule, memberId);

        return AuctionScheduleModifyResponse.of(auctionSchedule, current);
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

    public AuctionScheduleRemoveResponse removeAuctionSchedule(int auctionScheduleId, LocalDateTime current) {
        AuctionSchedule auctionSchedule = findAuctionScheduleById(auctionScheduleId);

        if (auctionSchedule.isNotRemovable()) {
            throw new AppException("경매 일정을 삭제할 수 없습니다.");
        }

        Long memberId = getMemberId();

        auctionSchedule.remove(memberId);

        return AuctionScheduleRemoveResponse.of(auctionSchedule, current);
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
