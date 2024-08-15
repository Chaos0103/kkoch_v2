package com.ssafy.user_service.api.service.notification;

import com.ssafy.user_service.api.PageResponse;
import com.ssafy.user_service.common.util.PageUtils;
import com.ssafy.user_service.domain.membernotification.repository.MemberNotificationQueryRepository;
import com.ssafy.user_service.domain.membernotification.repository.response.NotificationResponse;
import com.ssafy.user_service.domain.notification.NotificationCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationQueryService {

    private final MemberNotificationQueryRepository memberNotificationQueryRepository;

    public PageResponse<NotificationResponse> searchNotifications(String memberKey, String notificationCategory, int pageNumber) {
        NotificationCategory category = NotificationCategory.of(notificationCategory);

        Pageable pageable = PageUtils.of(pageNumber);

        List<NotificationResponse> content = memberNotificationQueryRepository.findAllByMemberKeyAndCond(memberKey, category, pageable);

        int total = memberNotificationQueryRepository.countByMemberKeyAndCond(memberKey, category);

        return PageResponse.create(content, pageable, total);
    }
}
