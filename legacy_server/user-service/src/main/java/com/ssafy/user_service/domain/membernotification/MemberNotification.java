package com.ssafy.user_service.domain.membernotification;

import com.ssafy.user_service.domain.TimeBaseEntity;
import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.notification.Notification;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberNotification extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isOpened;

    @Builder
    private MemberNotification(boolean isDeleted, Member member, Notification notification, boolean isOpened) {
        super(isDeleted);
        this.member = member;
        this.notification = notification;
        this.isOpened = isOpened;
    }

    public static MemberNotification of(boolean isDeleted, Member member, Notification notification, boolean isOpened) {
        return new MemberNotification(isDeleted, member, notification, isOpened);
    }

    public void open() {
        isOpened = true;
    }
}
