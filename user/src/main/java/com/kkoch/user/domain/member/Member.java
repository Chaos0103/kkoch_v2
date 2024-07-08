package com.kkoch.user.domain.member;

import com.kkoch.user.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

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

    @Column(nullable = false, columnDefinition = "int default 0")
    private int point;

    @Builder
    private Member(boolean isDeleted, String memberKey, String email, String pwd, String name, String tel, String businessNumber, int point) {
        super(isDeleted);
        this.memberKey = memberKey;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
        this.point = point;
    }

    //== 비즈니스 로직 ==//
    public void changePwd(String encryptedPwd) {
        this.pwd = encryptedPwd;
    }

    public void withdrawal() {
        super.remove();
    }
}