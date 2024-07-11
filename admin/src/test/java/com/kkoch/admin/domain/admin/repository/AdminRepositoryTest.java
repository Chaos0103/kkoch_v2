package com.kkoch.admin.domain.admin.repository;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.AdminAuth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AdminRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AdminRepository adminRepository;

    @DisplayName("이메일을 입력 받아 이메일 사용 여부를 조회한다.")
    @Test
    void existsByEmail1() {
        //given
        Admin admin = createAdmin();

        //when
        boolean isExist = adminRepository.existsByEmail("admin@ssafy.com");

        //then
        assertThat(isExist).isTrue();
    }

    @DisplayName("이메일을 입력 받아 이메일 사용 여부를 조회한다.")
    @Test
    void existsByEmail2() {
        //given //when
        boolean isExist = adminRepository.existsByEmail("admin@ssafy.com");

        //then
        assertThat(isExist).isFalse();
    }

    @DisplayName("연락처를 입력 받아 연락처 사용 여부를 조회한다.")
    @Test
    void existsByTel1() {
        //given
        Admin admin = createAdmin();

        //when
        boolean isExist = adminRepository.existsByTel("010-1234-1234");

        //then
        assertThat(isExist).isTrue();
    }

    @DisplayName("연락처를 입력 받아 연락처 사용 여부를 조회한다.")
    @Test
    void existsByTel2() {
        //given //when
        boolean isExist = adminRepository.existsByTel("010-1234-1234");

        //then
        assertThat(isExist).isFalse();
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