package com.ssafy.user_service.api.service.notification;

import com.ssafy.user_service.IntegrationTestSupport;
import com.ssafy.user_service.api.PageResponse;
import com.ssafy.user_service.api.service.SearchPeriod;
import com.ssafy.user_service.domain.member.*;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import com.ssafy.user_service.domain.member.vo.BankAccount;
import com.ssafy.user_service.domain.member.vo.MemberSpecificInfo;
import com.ssafy.user_service.domain.member.vo.Role;
import com.ssafy.user_service.domain.member.vo.UserAdditionalInfo;
import com.ssafy.user_service.domain.membernotification.MemberNotification;
import com.ssafy.user_service.domain.membernotification.repository.MemberNotificationRepository;
import com.ssafy.user_service.domain.membernotification.repository.response.NotificationResponse;
import com.ssafy.user_service.domain.notification.Notification;
import com.ssafy.user_service.domain.notification.NotificationCategory;
import com.ssafy.user_service.domain.notification.repository.NotificationRepository;
import com.ssafy.user_service.domain.notification.repository.response.SentNotificationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class NotificationQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private NotificationQueryService notificationQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MemberNotificationRepository memberNotificationRepository;

    @DisplayName("알림 카테고리와 페이지 번호를 입력 받아 회원의 알림 목록을 조회한다.")
    @Test
    void searchNotifications() {
        //given
        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        Notification notification1 = createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.", LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        createMemberNotification(user, notification1);

        Notification notification2 = createNotification(admin, NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.", LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        createMemberNotification(user, notification2);

        //when
        PageResponse<NotificationResponse> response = notificationQueryService.searchNotifications(user.getMemberKey(), "AUCTION", 0);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);
        assertThat(response.getContent()).hasSize(1)
            .extracting("id", "category", "content", "isOpened", "notificationDateTime")
            .containsExactly(
                tuple(notification2.getId(), NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.", false, notification2.getLastModifiedDateTime())
            );
    }

    @DisplayName("알림 카테고리와 페이지 번호를 입력 받아 회원의 알림 목록을 조회한다. 지원하지 않는 알림 카테고리인 경우 전체 조회를 한다.")
    @Test
    void searchNotificationsNotSupportedNotificationCategory() {
        //given
        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        Notification notification1 = createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.", LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        createMemberNotification(user, notification1);

        Notification notification2 = createNotification(admin, NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.", LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        createMemberNotification(user, notification2);

        //when
        PageResponse<NotificationResponse> response = notificationQueryService.searchNotifications(user.getMemberKey(), "NONE", 0);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);
        assertThat(response.getContent()).hasSize(2)
            .extracting("id", "category", "content", "isOpened", "notificationDateTime")
            .containsExactly(
                tuple(notification2.getId(), NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.", false, notification2.getLastModifiedDateTime()),
                tuple(notification1.getId(), NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.", false, notification1.getLastModifiedDateTime())
            );
    }

    @DisplayName("조회 기간을 입력 받아 보낸 알림 목록을 조회한다.")
    @Test
    void searchSentNotifications() {
        //given
        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.", LocalDateTime.of(2024, 8, 14, 23, 59, 59));
        Notification notification1 = createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        Notification notification2 = createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.", LocalDateTime.of(2024, 8, 15, 23, 59, 59));
        createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.", LocalDateTime.of(2024, 8, 16, 0, 0, 0));

        createMemberNotification(user, notification1);
        createMemberNotification(user, notification2);

        SearchPeriod searchPeriod = SearchPeriod.builder()
            .from("2024-08-15")
            .to("2024-08-15")
            .build();

        //when
        PageResponse<SentNotificationResponse> response = notificationQueryService.searchSentNotifications(searchPeriod, 0);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);
        assertThat(response.getContent()).hasSize(2)
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

    private Notification createNotification(Member member, NotificationCategory notificationCategory, String notificationContent, LocalDateTime notificationSentDateTime) {
        Notification notification = Notification.builder()
            .isDeleted(false)
            .member(member)
            .notificationCategory(notificationCategory)
            .notificationContent(notificationContent)
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