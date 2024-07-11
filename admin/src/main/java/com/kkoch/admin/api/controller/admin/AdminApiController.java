package com.kkoch.admin.api.controller.admin;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.admin.request.AdminCreateRequest;
import com.kkoch.admin.api.controller.admin.request.AdminPwdModifyRequest;
import com.kkoch.admin.api.service.admin.AdminService;
import com.kkoch.admin.api.service.admin.response.AdminCreateResponse;
import com.kkoch.admin.api.service.admin.response.AdminRemoveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-service/admins")
public class AdminApiController {

    private final AdminService adminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AdminCreateResponse> createAdmin(@Valid @RequestBody AdminCreateRequest request) {
        AdminCreateResponse response = adminService.createAdmin(request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @PatchMapping("/{adminId}")
    public ApiResponse<Integer> modifyAdminPwd(@PathVariable Integer adminId, @Valid @RequestBody AdminPwdModifyRequest request) {
        int removedAdminId = adminService.modifyPwd(adminId, request.toServiceRequest());

        return ApiResponse.ok(removedAdminId);
    }

    @DeleteMapping("/{adminId}")
    public ApiResponse<AdminRemoveResponse> removeAdmin(@PathVariable Integer adminId) {
        AdminRemoveResponse response = adminService.removeAdmin(adminId);

        return ApiResponse.ok(response);
    }
}
