package com.ssafy.user_service.api.controller.notification.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NotificationRemoveRequest {

    @NotEmpty(message = "알림 ID를 입력해주세요.")
    private List<Long> ids;

    @Builder
    private NotificationRemoveRequest(List<Long> ids) {
        this.ids = ids;
    }
}
