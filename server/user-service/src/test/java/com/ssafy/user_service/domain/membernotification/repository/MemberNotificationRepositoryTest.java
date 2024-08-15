package com.ssafy.user_service.domain.membernotification.repository;

import com.ssafy.user_service.IntegrationTestSupport;
import com.ssafy.user_service.domain.member.*;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import com.ssafy.user_service.domain.membernotification.MemberNotification;
import com.ssafy.user_service.domain.notification.Notification;
import com.ssafy.user_service.domain.notification.NotificationCategory;
import com.ssafy.user_service.domain.notification.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class MemberNotificationRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MemberNotificationRepository memberNotificationRepository;

    @DisplayName("알림 ID 목록을 입력 받아 열리지 않은 알림 목록을 조회한다.")
    @Test
    void findAllByIdInAndIsOpenedFalse() {
        //given
        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        Notification notification = createNotification(admin);
        MemberNotification memberNotification1 = createMemberNotification(user, notification, false, false);
        MemberNotification memberNotification2 = createMemberNotification(user, notification, true, false);
        MemberNotification memberNotification3 = createMemberNotification(user, notification, false, false);

        List<Long> notificationIds = List.of(memberNotification1.getId(), memberNotification2.getId(), memberNotification3.getId());

        //when
        List<MemberNotification> memberNotifications = memberNotificationRepository.findAllByIdInAndIsOpenedFalse(notificationIds);

        //then
        assertThat(memberNotifications).hasSize(2)
            .extracting("id", "isOpened")
            .containsExactlyInAnyOrder(
                tuple(memberNotification1.getId(), false),
                tuple(memberNotification3.getId(), false)
            );
    }

    @DisplayName("알림 ID 목록을 입력 받아 삭제되지 않은 알림 목록을 조회한다.")
    @Test
    void findAllByIdInAndIsDeletedFalse() {
        //given
        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        Notification notification = createNotification(admin);
        MemberNotification memberNotification1 = createMemberNotification(user, notification, false, false);
        MemberNotification memberNotification2 = createMemberNotification(user, notification, true, true);
        MemberNotification memberNotification3 = createMemberNotification(user, notification, false, false);

        List<Long> notificationIds = List.of(memberNotification1.getId(), memberNotification2.getId(), memberNotification3.getId());

        //when
        List<MemberNotification> memberNotifications = memberNotificationRepository.findAllByIdInAndIsDeletedFalse(notificationIds);

        //then
        assertThat(memberNotifications).hasSize(2)
            .extracting("id", "isDeleted")
            .containsExactlyInAnyOrder(
                tuple(memberNotification1.getId(), false),
                tuple(memberNotification3.getId(), false)
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

    private Notification createNotification(Member member) {
        Notification notification = Notification.builder()
            .isDeleted(false)
            .member(member)
            .notificationCategory(NotificationCategory.PAYMENT)
            .notificationContent("1,000,000원이 결제되었습니다.")
            .build();
        return notificationRepository.save(notification);
    }

    private MemberNotification createMemberNotification(Member member, Notification notification, boolean isOpened, boolean isDeleted) {
        MemberNotification memberNotification = MemberNotification.builder()
            .isDeleted(isDeleted)
            .member(member)
            .notification(notification)
            .isOpened(isOpened)
            .build();
        return memberNotificationRepository.save(memberNotification);
    }
}