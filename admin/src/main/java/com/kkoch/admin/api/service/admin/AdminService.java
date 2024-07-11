package com.kkoch.admin.api.service.admin;

import com.kkoch.admin.api.service.admin.request.AdminCreateServiceRequest;
import com.kkoch.admin.api.service.admin.request.AdminPwdModifyServiceRequest;
import com.kkoch.admin.api.service.admin.response.AdminCreateResponse;
import com.kkoch.admin.api.service.admin.response.AdminRemoveResponse;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import com.kkoch.admin.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminCreateResponse createAdmin(AdminCreateServiceRequest request) {
        boolean isExistEmail = adminRepository.existsByEmail(request.getEmail());
        if (isExistEmail) {
            throw new AppException("사용중인 이메일입니다.");
        }

        boolean isExistTel = adminRepository.existsByTel(request.getTel());
        if (isExistTel) {
            throw new AppException("사용중인 연락처입니다.");
        }

        Admin admin = request.toEntity(passwordEncoder.encode(request.getPwd()));
        Admin savedAdmin = adminRepository.save(admin);

        return AdminCreateResponse.of(savedAdmin);
    }

    public int modifyPwd(int adminId, AdminPwdModifyServiceRequest request) {
        Admin admin = findAdminBy(adminId);

        if (!passwordEncoder.matches(request.getCurrentPwd(), admin.getPwd())) {
            throw new AppException("현재 비밀번호가 일치하지 않습니다.");
        }

        admin.modifyPwd(passwordEncoder.encode(request.getNewPwd()));

        return adminId;
    }

    public AdminRemoveResponse removeAdmin(int adminId) {
        Admin admin = findAdminBy(adminId);

        admin.remove();

        return AdminRemoveResponse.of(admin);
    }

    private Admin findAdminBy(int adminId) {
        return adminRepository.findById(adminId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않는 관리자입니다."));
    }
}

