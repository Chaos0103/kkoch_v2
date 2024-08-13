package com.ssafy.user_service.domain.notification;

import com.ssafy.user_service.domain.TimeBaseEntity;
import com.ssafy.user_service.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    private Notification(boolean isDeleted, Member member, NotificationCategory notificationCategory, String notificationContent) {
        super(isDeleted);
        this.member = member;
        this.notificationCategory = notificationCategory;
        this.notificationContent = notificationContent;
    }

    public static Notification of(boolean isDeleted, Member member, NotificationCategory notificationCategory, String notificationContent) {
        return new Notification(isDeleted, member, notificationCategory, notificationContent);
    }
}