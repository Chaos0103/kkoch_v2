package com.ssafy.user_service.domain.membernotification.repository;

import com.ssafy.user_service.IntegrationTestSupport;
import com.ssafy.user_service.domain.member.*;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import com.ssafy.user_service.domain.membernotification.MemberNotification;
import com.ssafy.user_service.domain.membernotification.repository.response.NotificationResponse;
import com.ssafy.user_service.domain.notification.Notification;
import com.ssafy.user_service.domain.notification.NotificationCategory;
import com.ssafy.user_service.domain.notification.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberNotificationQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberNotificationQueryRepository memberNotificationQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MemberNotificationRepository memberNotificationRepository;

    @DisplayName("회원 고유키와 검색 조건과 일치하는 알림 목록을 조회한다.")
    @Test
    void findAllByMemberKeyAndCond() {
        //given
        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        Notification notification1 = createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.");
        createMemberNotification(user, notification1);

        Notification notification2 = createNotification(admin, NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.");
        createMemberNotification(user, notification2);

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        List<NotificationResponse> content = memberNotificationQueryRepository.findAllByMemberKeyAndCond(user.getMemberKey(), NotificationCategory.AUCTION, pageRequest);

        //then
        assertThat(content).hasSize(1)
            .extracting("id", "category", "content", "isOpened", "notificationDateTime")
            .containsExactly(
                tuple(notification2.getId(), NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.", false, notification2.getLastModifiedDateTime())
            );
    }

    @DisplayName("회원 고유키와 검색 조건과 일치하는 알림 목록을 조회한다. 알림 카테고리가 없는 경우 검색 조건에서 제외한다.")
    @Test
    void findAllByMemberKeyAndCondWithoutNotificationCategory() {
        //given
        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        Notification notification1 = createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.");
        createMemberNotification(user, notification1);

        Notification notification2 = createNotification(admin, NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.");
        createMemberNotification(user, notification2);

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        List<NotificationResponse> content = memberNotificationQueryRepository.findAllByMemberKeyAndCond(user.getMemberKey(), null, pageRequest);

        //then
        assertThat(content).hasSize(2)
            .extracting("id", "category", "content", "isOpened", "notificationDateTime")
            .containsExactly(
                tuple(notification2.getId(), NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.", false, notification2.getLastModifiedDateTime()),
                tuple(notification1.getId(), NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.", false, notification1.getLastModifiedDateTime())
            );
    }

    @DisplayName("회원 고유키와 검색 조건과 일치하는 알림 목록의 갯수를 조회한다.")
    @Test
    void countByMemberKeyAndCond() {
        //given
        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        Notification notification1 = createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.");
        createMemberNotification(user, notification1);

        Notification notification2 = createNotification(admin, NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.");
        createMemberNotification(user, notification2);

        //when
        int count = memberNotificationQueryRepository.countByMemberKeyAndCond(user.getMemberKey(), NotificationCategory.AUCTION);

        //then
        assertThat(count).isOne();
    }

    @DisplayName("회원 고유키와 검색 조건과 일치하는 알림 목록의 갯수를 조회한다.")
    @Test
    void countByMemberKeyAndCondWithoutNotificationCategory() {
        //given
        Member user = createMember(Role.USER, "ssafy@ssafy.com", "01012341234", UserAdditionalInfo.builder()
            .businessNumber("1231212345")
            .bankAccount(BankAccount.builder()
                .bankCode("088")
                .accountNumber("123123123456")
                .build())
            .build());
        Member admin = createMember(Role.ADMIN, "admin@ssafy.com", "01056785678", null);

        Notification notification1 = createNotification(admin, NotificationCategory.PAYMENT, "1,000,000원이 결제되었습니다.");
        createMemberNotification(user, notification1);

        Notification notification2 = createNotification(admin, NotificationCategory.AUCTION, "2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.");
        createMemberNotification(user, notification2);

        //when
        int count = memberNotificationQueryRepository.countByMemberKeyAndCond(user.getMemberKey(), null);

        //then
        assertThat(count).isEqualTo(2);
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