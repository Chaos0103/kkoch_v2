package com.kkoch.user.domain.reservation;

import com.kkoch.user.domain.TimeBaseEntity;
import com.kkoch.user.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    private int plantCount;

    @Column(nullable = false)
    private int desiredPrice;

    @Enumerated(STRING)
    @Column(nullable = false, length = 8)
    private PlantGrade plantGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private Integer plantId;

    @Builder
    public Reservation(boolean isDeleted, int plantCount, int desiredPrice, PlantGrade plantGrade, Member member, Integer plantId) {
        super(isDeleted);
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
        this.plantGrade = plantGrade;
        this.member = member;
        this.plantId = plantId;
    }

    public static Reservation of(boolean isDeleted, int plantCount, int desiredPrice, PlantGrade plantGrade, Member member, Integer plantId) {
        return new Reservation(isDeleted, plantCount, desiredPrice, plantGrade, member, plantId);
    }

    public static Reservation create(int plantCount, int desiredPrice, PlantGrade plantGrade, Member member, Integer plantId) {
        return of(false, plantCount, desiredPrice, plantGrade, member, plantId);
    }

    @Override
    public void remove() {
        super.remove();
    }
}
