package com.kkoch.admin.api.service.admin.response;

import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.AdminAuth;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminRemoveResponse {

    private final String email;
    private final String name;
    private final AdminAuth auth;
    private final LocalDateTime removedDateTime;

    @Builder
    private AdminRemoveResponse(String email, String name, AdminAuth auth, LocalDateTime removedDateTime) {
        this.email = email;
        this.name = name;
        this.auth = auth;
        this.removedDateTime = removedDateTime;
    }

    public static AdminRemoveResponse of(Admin admin) {
        return new AdminRemoveResponse(admin.getEmail(), admin.getName(), admin.getAuth(), admin.getLastModifiedDateTime());
    }
}
