package com.kkoch.admin.api.service.admin;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.admin.request.AdminCreateServiceRequest;
import com.kkoch.admin.api.service.admin.request.AdminPwdModifyServiceRequest;
import com.kkoch.admin.api.service.admin.response.AdminCreateResponse;
import com.kkoch.admin.api.service.admin.response.AdminRemoveResponse;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.AdminAuth;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import com.kkoch.admin.exception.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class AdminServiceTest extends IntegrationTestSupport {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @DisplayName("신규 관리자 등록시 입력 받은 이메일을 다른 관리자가 사용중이라면 예외가 발생한다.")
    @Test
    void createAdminWithDuplicationEmail() {
        //given
        Admin admin = createAdmin("admin@ssafy.com");

        AdminCreateServiceRequest request = AdminCreateServiceRequest.builder()
            .email("admin@ssafy.com")
            .pwd("ssafy1234!")
            .name("김관리")
            .tel("010-5678-5678")
            .auth("MASTER")
            .build();

        //when
        assertThatThrownBy(() -> adminService.createAdmin(request))
            .isInstanceOf(AppException.class)
            .hasMessage("사용중인 이메일입니다.");

        //then
        List<Admin> admins = adminRepository.findAll();
        assertThat(admins).hasSize(1);
    }

    @DisplayName("신규 관리자 등록시 입력 받은 연락처를 다른 관리자가 사용중이라면 예외가 발생한다.")
    @Test
    void createAdminWithDuplicationTel() {
        //given
        Admin admin = createAdmin("ssafy@ssafy.com");

        AdminCreateServiceRequest request = AdminCreateServiceRequest.builder()
            .email("admin@ssafy.com")
            .pwd("ssafy1234!")
            .name("김관리")
            .tel("010-1234-1234")
            .auth("MASTER")
            .build();

        //when
        assertThatThrownBy(() -> adminService.createAdmin(request))
            .isInstanceOf(AppException.class)
            .hasMessage("사용중인 연락처입니다.");

        //then
        List<Admin> admins = adminRepository.findAll();
        assertThat(admins).hasSize(1);
    }

    @DisplayName("관리자 정보를 입력 받아 신규 관리자를 등록한다.")
    @Test
    void createAdmin() {
        //given
        AdminCreateServiceRequest request = AdminCreateServiceRequest.builder()
            .email("admin@ssafy.com")
            .pwd("ssafy1234!")
            .name("김관리")
            .tel("010-1234-1234")
            .auth("MASTER")
            .build();

        //when
        AdminCreateResponse response = adminService.createAdmin(request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("email", "admin@ssafy.com")
            .hasFieldOrPropertyWithValue("name", "김관리")
            .hasFieldOrPropertyWithValue("auth", AdminAuth.MASTER);

        List<Admin> admins = adminRepository.findAll();
        assertThat(admins).hasSize(1);
    }

    @DisplayName("관리자 비밀번호 수정시 현재 비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void modifyAdminPwdNotMatchPwd() {
        //given
        Admin admin = createAdmin("ssafy@ssafy.com");
        AdminPwdModifyServiceRequest request = AdminPwdModifyServiceRequest.builder()
            .currentPwd("ssafy5678!")
            .newPwd("ssafy1234@")
            .build();

        //when
        assertThatThrownBy(() -> adminService.modifyPwd(admin.getId(), request))
            .isInstanceOf(AppException.class)
            .hasMessage("현재 비밀번호가 일치하지 않습니다.");

        //then
        Optional<Admin> findAdmin = adminRepository.findById(admin.getId());
        assertThat(findAdmin).isPresent();

        boolean isMatch = passwordEncoder.matches("ssafy1234!", findAdmin.get().getPwd());
        assertThat(isMatch).isTrue();
    }

    @DisplayName("비밀번호 정보를 입력 받아 관리자 비밀번호를 수정한다.")
    @Test
    void modifyAdminPwd() {
        //given
        Admin admin = createAdmin("ssafy@ssafy.com");
        AdminPwdModifyServiceRequest request = AdminPwdModifyServiceRequest.builder()
            .currentPwd("ssafy1234!")
            .newPwd("ssafy1234@")
            .build();

        //when
        int adminId = adminService.modifyPwd(admin.getId(), request);

        //then
        Optional<Admin> findAdmin = adminRepository.findById(adminId);
        assertThat(findAdmin).isPresent();

        boolean isMatch = passwordEncoder.matches("ssafy1234@", findAdmin.get().getPwd());
        assertThat(isMatch).isTrue();
    }

    @DisplayName("관리자 ID를 입력 받아 관리자를 삭제한다.")
    @Test
    void removeAdmin() {
        //given
        Admin admin = createAdmin("admin@ssafy.com");

        //when
        AdminRemoveResponse response = adminService.removeAdmin(admin.getId());

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("email", "admin@ssafy.com")
            .hasFieldOrPropertyWithValue("name", "김관리")
            .hasFieldOrPropertyWithValue("auth", AdminAuth.MASTER);

        Optional<Admin> findAdmin = adminRepository.findById(admin.getId());
        assertThat(findAdmin).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", true);
    }

    private Admin createAdmin(String email) {
        Admin admin = Admin.builder()
            .isDeleted(false)
            .email(email)
            .pwd(passwordEncoder.encode("ssafy1234!"))
            .name("김관리")
            .tel("010-1234-1234")
            .auth(AdminAuth.MASTER)
            .build();
        return adminRepository.save(admin);
    }
}