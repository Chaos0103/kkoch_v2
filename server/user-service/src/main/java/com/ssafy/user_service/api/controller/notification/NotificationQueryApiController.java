package com.ssafy.user_service.api.controller.notification;

import com.ssafy.user_service.api.ApiResponse;
import com.ssafy.user_service.api.PageResponse;
import com.ssafy.user_service.api.controller.notification.param.NotificationSearchParam;
import com.ssafy.user_service.api.service.notification.NotificationQueryService;
import com.ssafy.user_service.domain.membernotification.repository.response.NotificationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ssafy.user_service.common.security.SecurityUtils.findMemberKeyByToken;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationQueryApiController {

    private final NotificationQueryService notificationQueryService;

    @GetMapping
    public ApiResponse<PageResponse<NotificationResponse>> searchNotifications(@Valid @ModelAttribute NotificationSearchParam param) {
        String memberKey = findMemberKeyByToken();

        PageResponse<NotificationResponse> response = notificationQueryService.searchNotifications(memberKey, param.getCategory(), param.toNumberPage());

        return ApiResponse.ok(response);
    }
}
