package com.kkoch.user.domain.member;

import com.kkoch.user.domain.TimeBaseEntity;
import com.kkoch.user.domain.pointlog.PointStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "char(36)", length = 36)
    private String memberKey;

    @Column(unique = true, nullable = false, updatable = false, length = 100)
    private String email;

    @Column(nullable = false, columnDefinition = "char(60)", length = 60)
    private String pwd;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(unique = true, nullable = false, columnDefinition = "char(13)", length = 13)
    private String tel;

    @Column(unique = true, nullable = false, updatable = false, length = 12)
    private String businessNumber;

    @Embedded
    private Point point;

    @Builder
    private Member(boolean isDeleted, String memberKey, String email, String pwd, String name, String tel, String businessNumber, Point point) {
        super(isDeleted);
        this.memberKey = memberKey;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
        this.point = point;
    }

    public static Member of(boolean isDeleted, String memberKey, String email, String pwd, String name, String tel, String businessNumber, Point point) {
        return new Member(isDeleted, memberKey, email, pwd, name, tel, businessNumber, point);
    }

    public static Member create(String email, String pwd, String name, String tel, String businessNumber) {
        String memberKey = UUID.randomUUID().toString();
        return of(false, memberKey, email, pwd, name, tel, businessNumber, Point.init());
    }

    public void modifyPwd(String pwd) {
        this.pwd = pwd;
    }

    public void modifyPoint(PointStatus status, int amount) {
        if (status == PointStatus.CHARGE) {
            point = point.add(amount);
            return;
        }
        point = point.subtract(amount);
    }

    public void withdrawal() {
        super.remove();
    }
}