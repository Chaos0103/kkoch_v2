package com.kkoch.admin.api.service.variety;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.variety.request.VarietyCreateServiceRequest;
import com.kkoch.admin.api.service.variety.response.VarietyCreateResponse;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.AdminAuth;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import com.kkoch.admin.domain.variety.repository.VarietyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class VarietyServiceTest extends IntegrationTestSupport {

    @Autowired
    private VarietyService varietyService;

    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private AdminRepository adminRepository;

    @DisplayName("품종 정보를 입력 받아 품종을 신규 등록한다.")
    @Test
    void createVariety() {
        //given
        Admin admin = createAdmin();
        VarietyCreateServiceRequest request = VarietyCreateServiceRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();

        //when
        VarietyCreateResponse response = varietyService.createVariety(admin.getId(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("varietyCode", "10000001")
            .hasFieldOrPropertyWithValue("plantCategory", PlantCategory.CUT_FLOWERS)
            .hasFieldOrPropertyWithValue("itemName", "장미")
            .hasFieldOrPropertyWithValue("varietyName", "하트앤소울");

        List<Variety> varieties = varietyRepository.findAll();
        assertThat(varieties).hasSize(1);
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
}