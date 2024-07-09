package com.kkoch.user.domain.pointlog;

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
public class PointLog extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_log_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 10)
    private Bank bank;

    @Column(nullable = false, updatable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 7)
    private PointStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private PointLog(boolean isDeleted, Bank bank, int amount, PointStatus status, Member member) {
        super(isDeleted);
        this.bank = bank;
        this.amount = amount;
        this.status = status;
        this.member = member;
    }

    public static PointLog of(boolean isDeleted, Bank bank, int amount, PointStatus status, Member member) {
        return new PointLog(isDeleted, bank, amount, status, member);
    }

    public static PointLog create(Bank bank, int amount, PointStatus status, Member member) {
        member.modifyPoint(status, amount);
        return of(false, bank, amount, status, member);
    }
}
