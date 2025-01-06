package com.ssafy.user_service.domain.notification;

import com.ssafy.user_service.domain.TimeBaseEntity;
import com.ssafy.user_service.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, columnDefinition = "char(7)", length = 7)
    private NotificationCategory notificationCategory;

    @Column(nullable = false, updatable = false, length = 100)
    private String notificationContent;

    @Column(nullable = false, updatable = false)
    private LocalDateTime notificationSentDateTime;

    @Builder
    private Notification(boolean isDeleted, Member member, NotificationCategory notificationCategory, String notificationContent, LocalDateTime notificationSentDateTime) {
        super(isDeleted);
        this.member = member;
        this.notificationCategory = notificationCategory;
        this.notificationContent = notificationContent;
        this.notificationSentDateTime = notificationSentDateTime;
    }

    public static Notification of(boolean isDeleted, Member member, NotificationCategory notificationCategory, String notificationContent, LocalDateTime notificationSentDateTime) {
        return new Notification(isDeleted, member, notificationCategory, notificationContent, notificationSentDateTime);
    }
}