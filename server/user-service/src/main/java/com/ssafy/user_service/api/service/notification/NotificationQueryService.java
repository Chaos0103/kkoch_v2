package com.ssafy.user_service.api.service.notification;

import com.ssafy.user_service.api.PageResponse;
import com.ssafy.user_service.api.service.SearchPeriod;
import com.ssafy.user_service.common.util.PageUtils;
import com.ssafy.user_service.domain.membernotification.repository.MemberNotificationQueryRepository;
import com.ssafy.user_service.domain.membernotification.repository.response.NotificationResponse;
import com.ssafy.user_service.domain.notification.NotificationCategory;
import com.ssafy.user_service.domain.notification.repository.NotificationQueryRepository;
import com.ssafy.user_service.domain.notification.repository.response.SentNotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationQueryService {

    private final NotificationQueryRepository notificationQueryRepository;
    private final MemberNotificationQueryRepository memberNotificationQueryRepository;

    public PageResponse<NotificationResponse> searchNotifications(String memberKey, String notificationCategory, int pageNumber) {
        NotificationCategory category = NotificationCategory.of(notificationCategory);

        Pageable pageable = PageUtils.of(pageNumber);

        List<NotificationResponse> content = memberNotificationQueryRepository.findAllByMemberKeyAndCond(memberKey, category, pageable);

        int total = memberNotificationQueryRepository.countByMemberKeyAndCond(memberKey, category);

        return PageResponse.create(content, pageable, total);
    }

    public PageResponse<SentNotificationResponse> searchSentNotifications(SearchPeriod period, int pageNumber) {
        Pageable pageable = PageUtils.of(pageNumber);

        period.valid();

        List<SentNotificationResponse> content = notificationQueryRepository.findAllByNotificationSentDateTimeBetween(period.getFrom(), period.getTo(), pageable);

        int total = notificationQueryRepository.countByNotificationSentDateTimeBetween(period.getFrom(), period.getTo());

        return PageResponse.create(content, pageable, total);
    }
}
