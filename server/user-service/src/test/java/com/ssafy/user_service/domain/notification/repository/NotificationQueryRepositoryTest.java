package com.ssafy.user_service.domain.notification.repository;

import com.ssafy.user_service.IntegrationTestSupport;
import com.ssafy.user_service.domain.member.*;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import com.ssafy.user_service.domain.membernotification.MemberNotification;
import com.ssafy.user_service.domain.membernotification.repository.MemberNotificationRepository;
import com.ssafy.user_service.domain.notification.Notification;
import com.ssafy.user_service.domain.notification.NotificationCategory;
import com.ssafy.user_service.domain.notification.repository.response.SentNotificationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class NotificationQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private NotificationQueryRepository notificationQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MemberNotificationRepository memberNotificationRepository;

    @DisplayName("시작일과 종료일 사이에 보낸 알림 목록을 조회한다.")
    @Test
    void findAllByNotificationSentDateTimeBetween() {
        //given
        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        createNotification(admin, LocalDateTime.of(2024, 8, 14, 23, 59, 59));
        Notification notification1 = createNotification(admin, LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        Notification notification2 = createNotification(admin, LocalDateTime.of(2024, 8, 15, 23, 59, 59));
        createNotification(admin, LocalDateTime.of(2024, 8, 16, 0, 0, 0));

        createMemberNotification(user, notification1);
        createMemberNotification(user, notification2);

        LocalDateTime searchStartDateTime = LocalDateTime.of(2024, 8, 15, 0, 0, 0);
        LocalDateTime searchEndDateTime = LocalDateTime.of(2024, 8, 16, 0, 0, 0);
        PageRequest pageRequest = PageRequest.of(0, 10);
        //when
        List<SentNotificationResponse> content = notificationQueryRepository.findAllByNotificationSentDateTimeBetween(searchStartDateTime, searchEndDateTime, pageRequest);

        //then
        assertThat(content).hasSize(2)
            .extracting("id", "category", "sentMemberCount")
            .containsExactly(
                tuple(notification2.getId(), NotificationCategory.PAYMENT, 1L),
                tuple(notification1.getId(), NotificationCategory.PAYMENT, 1L)
            );
    }

    private Member createMember(Role role, String email, String tel, UserAdditionalInfo userAdditionalInfo) {
        Member member = Member.builder()
            .isDeleted(false)
            .specificInfo(MemberSpecificInfo.builder()
                .memberKey(generateMemberKey())
                .role(role)
                .build())
            .email(email)
            .pwd(passwordEncoder.encode("ssafy1234!"))
            .name("김싸피")
            .tel(tel)
            .userAdditionalInfo(userAdditionalInfo)
            .build();
        return memberRepository.save(member);
    }

    private Notification createNotification(Member member, LocalDateTime notificationSentDateTime) {
        Notification notification = Notification.builder()
            .isDeleted(false)
            .member(member)
            .notificationCategory(NotificationCategory.PAYMENT)
            .notificationContent("1,000,000원이 결제되었습니다.")
            .notificationSentDateTime(notificationSentDateTime)
            .build();
        return notificationRepository.save(notification);
    }

    private MemberNotification createMemberNotification(Member member, Notification notification) {
        MemberNotification memberNotification = MemberNotification.builder()
            .isDeleted(false)
            .member(member)
            .notification(notification)
            .isOpened(false)
            .build();
        return memberNotificationRepository.save(memberNotification);
    }
}