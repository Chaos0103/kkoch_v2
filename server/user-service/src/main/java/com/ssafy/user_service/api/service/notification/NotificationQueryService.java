package com.ssafy.user_service.api.service.notification;

import com.ssafy.user_service.api.PageResponse;
import com.ssafy.user_service.domain.membernotification.repository.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationQueryService {

    public PageResponse<NotificationResponse> searchNotifications(String memberKey, String notificationCategory, int page) {
        return null;
    }
}
