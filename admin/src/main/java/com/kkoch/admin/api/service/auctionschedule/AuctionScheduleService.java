package com.kkoch.admin.api.service.auctionschedule;

import com.kkoch.admin.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import com.kkoch.admin.api.service.auctionschedule.request.AuctionScheduleModifyServiceRequest;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleModifyResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleRemoveResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleStatusResponse;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionScheduleService {

    private final AuctionScheduleRepository auctionScheduleRepository;
    private final AdminRepository adminRepository;

    public AuctionScheduleCreateResponse createAuctionSchedule(int adminId, AuctionScheduleCreateServiceRequest request) {
        Admin admin = findAdminBy(adminId);

        AuctionSchedule auctionSchedule = request.toEntity(admin);
        AuctionSchedule savedAuctionSchedule = auctionScheduleRepository.save(auctionSchedule);

        return AuctionScheduleCreateResponse.of(savedAuctionSchedule);
    }

    public AuctionScheduleModifyResponse modifyAuctionSchedule(int adminId, int auctionScheduleId, AuctionScheduleModifyServiceRequest request) {
        Admin admin = findAdminBy(adminId);
        AuctionSchedule auctionSchedule = findAuctionScheduleBy(auctionScheduleId);

        auctionSchedule.modify(request.getCode(), request.getAuctionDateTime(), admin);

        return AuctionScheduleModifyResponse.of(auctionSchedule);
    }

    public AuctionScheduleStatusResponse modifyAuctionRoomStatus(int adminId, int auctionScheduleId, AuctionRoomStatus status) {
        Admin admin = findAdminBy(adminId);
        AuctionSchedule auctionSchedule = findAuctionScheduleBy(auctionScheduleId);

        if (status == AuctionRoomStatus.READY) {
            auctionSchedule.ready(admin);
        }
        if (status == AuctionRoomStatus.OPEN) {
            auctionSchedule.open(admin);
        }
        if (status == AuctionRoomStatus.CLOSE) {
            auctionSchedule.close(admin);
        }

        return AuctionScheduleStatusResponse.of(auctionSchedule);
    }

    public AuctionScheduleRemoveResponse removeAuctionSchedule(int adminId, int auctionScheduleId) {
        Admin admin = findAdminBy(adminId);
        AuctionSchedule auctionSchedule = findAuctionScheduleBy(auctionScheduleId);

        auctionSchedule.remove(admin);

        return AuctionScheduleRemoveResponse.of(auctionSchedule);
    }

    private Admin findAdminBy(int adminId) {
        return adminRepository.findById(adminId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 관리자입니다."));
    }

    private AuctionSchedule findAuctionScheduleBy(int auctionScheduleId) {
        return auctionScheduleRepository.findById(auctionScheduleId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 경매 일정입니다."));
    }
}
