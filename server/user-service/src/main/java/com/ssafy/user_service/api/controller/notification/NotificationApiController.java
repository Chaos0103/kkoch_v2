package com.ssafy.user_service.api.controller.notification;

import com.ssafy.user_service.api.ApiResponse;
import com.ssafy.user_service.api.controller.notification.request.NotificationOpenRequest;
import com.ssafy.user_service.api.service.notification.NotificationService;
import com.ssafy.user_service.api.service.notification.response.NotificationOpenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationApiController {

    private final NotificationService notificationService;

    @PostMapping("/open")
    public ApiResponse<NotificationOpenResponse> openNotifications(@Valid @RequestBody NotificationOpenRequest request) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        NotificationOpenResponse response = notificationService.openNotifications(request.getIds(), currentDateTime);

        return ApiResponse.ok(response);
    }
}
