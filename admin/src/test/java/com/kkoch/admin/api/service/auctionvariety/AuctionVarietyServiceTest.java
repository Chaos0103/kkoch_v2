package com.kkoch.admin.api.service.auctionvariety;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.auctionvariety.request.AuctionVarietyCreateServiceRequest;
import com.kkoch.admin.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.AdminAuth;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import com.kkoch.admin.domain.auctionvariety.repository.AuctionVarietyRepository;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import com.kkoch.admin.domain.variety.repository.VarietyRepository;
import com.kkoch.admin.exception.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuctionVarietyServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionVarietyService auctionVarietyService;

    @Autowired
    private AuctionVarietyRepository auctionVarietyRepository;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private AdminRepository adminRepository;

    @DisplayName("경매 품종 신규 등록시 품종의 화훼부류와 경매 일정의 화훼부류가 일치하지 않으면 예외가 발생한다.")
    @Test
    void createAuctionVarietyNotEqualPlantCategory() {
        //given
        Admin admin = createAdmin();
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.ORCHID);
        AuctionVarietyCreateServiceRequest request = AuctionVarietyCreateServiceRequest.builder()
            .grade("SUPER")
            .plantCount(10)
            .startPrice(4500)
            .region("광주")
            .shipper("김광주")
            .build();

        //when
        assertThatThrownBy(() -> auctionVarietyService.createAuctionVariety(admin.getId(), variety.getCode(), auctionSchedule.getId(), request))
            .isInstanceOf(AppException.class)
            .hasMessage("해당 경매 일정에 등록할 수 없는 품종입니다.");

        //then
        List<AuctionVariety> auctionVarieties = auctionVarietyRepository.findAll();
        assertThat(auctionVarieties).isEmpty();
    }

    @DisplayName("경매 품종 정보를 입력 받아 경매 품종을 신규 등록한다.")
    @Test
    void createAuctionVariety() {
        //given
        Admin admin = createAdmin();
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.CUT_FLOWERS);
        AuctionVarietyCreateServiceRequest request = AuctionVarietyCreateServiceRequest.builder()
            .grade("SUPER")
            .plantCount(10)
            .startPrice(4500)
            .region("광주")
            .shipper("김광주")
            .build();

        //when
        AuctionVarietyCreateResponse response = auctionVarietyService.createAuctionVariety(admin.getId(), variety.getCode(), auctionSchedule.getId(), request);

        //then
        assertThat(response).isNotNull();

        List<AuctionVariety> auctionVarieties = auctionVarietyRepository.findAll();
        assertThat(auctionVarieties).hasSize(1);
    }

    private Admin createAdmin() {
        Admin admin = Admin.builder()
            .isDeleted(false)
            .email("admin@ssafy.com")
            .pwd(passwordEncoder.encode("ssafy1234!"))
            .name("김관리")
            .tel("010-1234-1234")
            .auth(AdminAuth.MASTER)
            .build();
        return adminRepository.save(admin);
    }

    private Variety createVariety() {
        Variety variety = Variety.builder()
            .isDeleted(false)
            .createdBy(1)
            .lastModifiedBy(1)
            .code("10000001")
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();
        return varietyRepository.save(variety);
    }

    private AuctionSchedule createAuctionSchedule(PlantCategory plantCategory) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .plantCategory(plantCategory)
            .roomStatus(AuctionRoomStatus.READY)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }
}