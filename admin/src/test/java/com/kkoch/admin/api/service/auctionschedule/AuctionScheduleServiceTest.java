package com.kkoch.admin.api.service.auctionschedule;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import com.kkoch.admin.api.service.auctionschedule.request.AuctionScheduleModifyServiceRequest;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleModifyResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleRemoveResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleStatusResponse;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.AdminAuth;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AuctionScheduleServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionScheduleService auctionScheduleService;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @Autowired
    private AdminRepository adminRepository;

    @DisplayName("경매 일정 정보를 입력 받아 경매 일정을 신규 등록한다.")
    @Test
    void createAuctionSchedule() {
        //given
        Admin admin = createAdmin();
        AuctionScheduleCreateServiceRequest request = AuctionScheduleCreateServiceRequest.builder()
            .code("CUT_FLOWERS")
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();

        //when
        AuctionScheduleCreateResponse response = auctionScheduleService.createAuctionSchedule(admin.getId(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("code", PlantCategory.CUT_FLOWERS)
            .hasFieldOrPropertyWithValue("roomStatus", AuctionRoomStatus.INIT)
            .hasFieldOrPropertyWithValue("auctionDateTime", LocalDateTime.of(2024, 7, 12, 5, 0));

        List<AuctionSchedule> auctionSchedules = auctionScheduleRepository.findAll();
        assertThat(auctionSchedules).hasSize(1);
    }

    @DisplayName("경매 일정 정보를 입력 받아 경매 일정을 수정한다.")
    @Test
    void modifyAuctionSchedule() {
        //given
        Admin admin = createAdmin();
        AuctionSchedule auctionSchedule = createAuctionSchedule(admin);

        AuctionScheduleModifyServiceRequest request = AuctionScheduleModifyServiceRequest.builder()
            .code("ORCHID")
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 7, 0))
            .build();

        //when
        AuctionScheduleModifyResponse response = auctionScheduleService.modifyAuctionSchedule(admin.getId(), auctionSchedule.getId(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("code", PlantCategory.ORCHID)
            .hasFieldOrPropertyWithValue("roomStatus", AuctionRoomStatus.INIT)
            .hasFieldOrPropertyWithValue("auctionDateTime", LocalDateTime.of(2024, 7, 12, 7, 0));

        Optional<AuctionSchedule> findActionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findActionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("plantCategory", PlantCategory.ORCHID)
            .hasFieldOrPropertyWithValue("auctionDateTime", LocalDateTime.of(2024, 7, 12, 7, 0));
    }

    @DisplayName("경매 방 상태 정보를 입력 받아 경매 방 상태를 수정한다.")
    @CsvSource({"READY", "OPEN", "CLOSE"})
    @ParameterizedTest
    void modifyAuctionRoomStatus(AuctionRoomStatus status) {
        //given
        Admin admin = createAdmin();
        AuctionSchedule auctionSchedule = createAuctionSchedule(admin);

        //when
        AuctionScheduleStatusResponse response = auctionScheduleService.modifyAuctionRoomStatus(admin.getId(), auctionSchedule.getId(), status);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("auctionScheduleId", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("code", PlantCategory.CUT_FLOWERS)
            .hasFieldOrPropertyWithValue("roomStatus", status)
            .hasFieldOrPropertyWithValue("auctionDateTime", LocalDateTime.of(2024, 7, 12, 5, 0));

        Optional<AuctionSchedule> findActionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findActionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("roomStatus", status);
    }

    @DisplayName("경매 일정 ID를 입력 받아 경매 일정을 삭제한다.")
    @Test
    void removeAuctionSchedule() {
        //given
        Admin admin = createAdmin();
        AuctionSchedule auctionSchedule = createAuctionSchedule(admin);

        //when
        AuctionScheduleRemoveResponse response = auctionScheduleService.removeAuctionSchedule(admin.getId(), auctionSchedule.getId());

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("auctionScheduleId", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("code", PlantCategory.CUT_FLOWERS)
            .hasFieldOrPropertyWithValue("roomStatus", AuctionRoomStatus.INIT)
            .hasFieldOrPropertyWithValue("auctionDateTime", LocalDateTime.of(2024, 7, 12, 5, 0));

        Optional<AuctionSchedule> findActionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findActionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", true);
    }

    public Admin createAdmin() {
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

    public AuctionSchedule createAuctionSchedule(Admin admin) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(admin.getId())
            .lastModifiedBy(admin.getId())
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .roomStatus(AuctionRoomStatus.INIT)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }
}