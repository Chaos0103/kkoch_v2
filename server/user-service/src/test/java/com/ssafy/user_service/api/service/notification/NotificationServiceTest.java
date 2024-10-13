package com.ssafy.user_service.api.service.notification;

import com.ssafy.user_service.IntegrationTestSupport;
import com.ssafy.user_service.api.service.notification.response.NotificationOpenResponse;
import com.ssafy.user_service.api.service.notification.response.NotificationRemoveResponse;
import com.ssafy.user_service.domain.member.*;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import com.ssafy.user_service.domain.member.vo.BankAccount;
import com.ssafy.user_service.domain.member.vo.MemberSpecificInfo;
import com.ssafy.user_service.domain.member.vo.Role;
import com.ssafy.user_service.domain.member.vo.UserAdditionalInfo;
import com.ssafy.user_service.domain.membernotification.MemberNotification;
import com.ssafy.user_service.domain.membernotification.repository.MemberNotificationRepository;
import com.ssafy.user_service.domain.notification.Notification;
import com.ssafy.user_service.domain.notification.NotificationCategory;
import com.ssafy.user_service.domain.notification.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationServiceTest extends IntegrationTestSupport {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MemberNotificationRepository memberNotificationRepository;

    @DisplayName("알림 ID 목록을 입력 받아 알림을 연다.")
    @Test
    void openNotifications() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();

        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        Notification notification1 = createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.");
        MemberNotification memberNotification1 = createMemberNotification(user, notification1);

        Notification notification2 = createNotification(admin, NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.");
        MemberNotification memberNotification2 = createMemberNotification(user, notification2);

        List<Long> notificationIds = List.of(memberNotification1.getId(), memberNotification2.getId());

        //when
        NotificationOpenResponse response = notificationService.openNotifications(notificationIds, currentDateTime);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("openNotificationCount", 2)
            .hasFieldOrPropertyWithValue("openStatusModifiedDateTime", currentDateTime);

        List<MemberNotification> memberNotifications = memberNotificationRepository.findAll();
        assertThat(memberNotifications).hasSize(2)
            .extracting("isOpened")
            .containsExactlyInAnyOrder(true, true);
    }

    @DisplayName("알림 ID 목록을 입력 받아 알림을 삭제한다.")
    @Test
    void removeNotifications() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();

        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        Notification notification1 = createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.");
        MemberNotification memberNotification1 = createMemberNotification(user, notification1);

        Notification notification2 = createNotification(admin, NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.");
        MemberNotification memberNotification2 = createMemberNotification(user, notification2);

        List<Long> notificationIds = List.of(memberNotification1.getId(), memberNotification2.getId());

        //when
        NotificationRemoveResponse response = notificationService.removeNotifications(notificationIds, currentDateTime);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("removedNotificationCount", 2)
            .hasFieldOrPropertyWithValue("removedDateTime", currentDateTime);

        List<MemberNotification> memberNotifications = memberNotificationRepository.findAll();
        assertThat(memberNotifications).hasSize(2)
            .extracting("isDeleted")
            .containsExactlyInAnyOrder(true, true);
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

    private Notification createNotification(Member member, NotificationCategory notificationCategory, String notificationContent) {
        Notification notification = Notification.builder()
            .isDeleted(false)
            .member(member)
            .notificationCategory(notificationCategory)
            .notificationContent(notificationContent)
            .notificationSentDateTime(LocalDateTime.of(2024, 1, 1, 0, 0))
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