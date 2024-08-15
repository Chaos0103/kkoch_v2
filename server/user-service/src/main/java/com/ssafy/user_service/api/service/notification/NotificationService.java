package com.ssafy.user_service.api.service.notification;

import com.ssafy.user_service.api.service.notification.response.NotificationOpenResponse;
import com.ssafy.user_service.domain.membernotification.repository.MemberNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final MemberNotificationRepository memberNotificationRepository;

    public NotificationOpenResponse openNotifications(List<Long> notificationIds, LocalDateTime currentDateTime) {
        return null;
    }
}
