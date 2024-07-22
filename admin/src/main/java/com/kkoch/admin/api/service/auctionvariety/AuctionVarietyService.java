package com.kkoch.admin.api.service.auctionvariety;

import com.kkoch.admin.api.service.auctionvariety.request.AuctionVarietyCreateServiceRequest;
import com.kkoch.admin.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import com.kkoch.admin.domain.auctionvariety.repository.AuctionVarietyRepository;
import com.kkoch.admin.domain.variety.Variety;
import com.kkoch.admin.domain.variety.repository.VarietyRepository;
import com.kkoch.admin.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionVarietyService {

    private final AuctionVarietyRepository auctionVarietyRepository;
    private final AuctionScheduleRepository auctionScheduleRepository;
    private final VarietyRepository varietyRepository;
    private final AdminRepository adminRepository;

    public AuctionVarietyCreateResponse createAuctionVariety(int adminId, String varietyCode, int auctionScheduleId, AuctionVarietyCreateServiceRequest request) {
        Admin admin = findAdminBy(adminId);

        Variety variety = findVarietyBy(varietyCode);

        AuctionSchedule auctionSchedule = findAuctionScheduleBy(auctionScheduleId);
        if (auctionSchedule.isNotEqualPlantCategory(variety)) {
            throw new AppException("해당 경매 일정에 등록할 수 없는 품종입니다.");
        }

        String auctionNumber = generateAuctionNumber(auctionSchedule);

        AuctionVariety auctionVariety = request.toEntity(auctionNumber, admin, auctionSchedule, variety);
        AuctionVariety savedAuctionVariety = auctionVarietyRepository.save(auctionVariety);

        return AuctionVarietyCreateResponse.of(savedAuctionVariety);
    }

    private Admin findAdminBy(int adminId) {
        return adminRepository.findById(adminId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 회원입니다."));
    }

    private AuctionSchedule findAuctionScheduleBy(int auctionScheduleId) {
        return auctionScheduleRepository.findById(auctionScheduleId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 경매 일정입니다."));
    }

    private Variety findVarietyBy(String varietyCode) {
        return varietyRepository.findById(varietyCode)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 품종입니다."));
    }

    private String generateAuctionNumber(AuctionSchedule auctionSchedule) {
        int nextAuctionNumber = auctionVarietyRepository.countByAuctionSchedule(auctionSchedule) + 1;
        return String.format("%5d", nextAuctionNumber);
    }
}
