package com.kkoch.admin.api.service.admin.response;

import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.AdminAuth;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminCreateResponse {

    private final String email;
    private final String name;
    private final AdminAuth auth;
    private final LocalDateTime createdDateTime;

    @Builder
    private AdminCreateResponse(String email, String name, AdminAuth auth, LocalDateTime createdDateTime) {
        this.email = email;
        this.name = name;
        this.auth = auth;
        this.createdDateTime = createdDateTime;
    }

    public static AdminCreateResponse of(Admin admin) {
        return new AdminCreateResponse(admin.getEmail(), admin.getName(), admin.getAuth(), admin.getCreatedDateTime());
    }
}