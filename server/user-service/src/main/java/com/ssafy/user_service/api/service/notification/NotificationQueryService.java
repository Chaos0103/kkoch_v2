package com.ssafy.user_service.api.service.notification;

import com.ssafy.user_service.api.PageResponse;
import com.ssafy.user_service.domain.membernotification.repository.MemberNotificationQueryRepository;
import com.ssafy.user_service.domain.membernotification.repository.response.NotificationResponse;
import com.ssafy.user_service.domain.notification.NotificationCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationQueryService {

    private final MemberNotificationQueryRepository memberNotificationQueryRepository;

    public PageResponse<NotificationResponse> searchNotifications(String memberKey, String notificationCategory, int page) {
        NotificationCategory category = NotificationCategory.of(notificationCategory);

        PageRequest pageRequest = PageRequest.of(page, 10);

        List<NotificationResponse> content = memberNotificationQueryRepository.findAllByMemberKeyAndCond(memberKey, category, pageRequest);

        int total = memberNotificationQueryRepository.countByMemberKeyAndCond(memberKey, category);

        return PageResponse.create(content, pageRequest, total);
    }
}
