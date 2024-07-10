package com.kkoch.user.domain.alarm;

import com.kkoch.user.domain.TimeBaseEntity;
import com.kkoch.user.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @Column(nullable = false, updatable = false, length = 100)
    private String content;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isOpened;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Alarm(boolean isDeleted, String content, boolean isOpened, Member member) {
        super(isDeleted);
        this.content = content;
        this.isOpened = isOpened;
        this.member = member;
    }

    public static Alarm of(boolean isDeleted, String content, boolean isOpened, Member member) {
        return new Alarm(isDeleted, content, isOpened, member);
    }

    public static Alarm create(String content, Member member) {
        return of(false, content, false, member);
    }

    public void open() {
        isOpened = true;
    }
}
