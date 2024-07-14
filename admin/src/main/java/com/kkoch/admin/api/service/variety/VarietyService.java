package com.kkoch.admin.api.service.variety;

import com.kkoch.admin.api.service.variety.request.VarietyCreateServiceRequest;
import com.kkoch.admin.api.service.variety.response.VarietyCreateResponse;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import com.kkoch.admin.domain.variety.Variety;
import com.kkoch.admin.domain.variety.repository.VarietyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class VarietyService {

    private final VarietyRepository varietyRepository;
    private final AdminRepository adminRepository;

    public VarietyCreateResponse createVariety(int adminId, VarietyCreateServiceRequest request) {
        Admin admin = findAdminBy(adminId);

        int count = varietyRepository.countByPlantCategory(request.getPlantCategory());

        Variety variety = request.toEntity(count + 1, admin);
        Variety savedVariety = varietyRepository.save(variety);

        return VarietyCreateResponse.of(savedVariety);
    }

    private Admin findAdminBy(int adminId) {
        return adminRepository.findById(adminId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 관리자입니다."));
    }
}
