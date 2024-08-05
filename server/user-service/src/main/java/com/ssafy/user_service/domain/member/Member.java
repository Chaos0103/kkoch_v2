package com.ssafy.user_service.domain.member;

import com.ssafy.user_service.domain.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private MemberSpecificInfo specificInfo;

    @Column(unique = true, nullable = false, updatable = false, length = 100)
    private String email;

    @Column(nullable = false, columnDefinition = "char(60)", length = 60)
    private String pwd;

    @Column(nullable = false, updatable = false, length = 20)
    private String name;

    @Column(unique = true, nullable = false, columnDefinition = "char(11)", length = 11)
    private String tel;

    @Embedded
    private UserAdditionalInfo userAdditionalInfo;

    @Builder
    private Member(boolean isDeleted, MemberSpecificInfo specificInfo, String email, String pwd, String name, String tel, UserAdditionalInfo userAdditionalInfo) {
        super(isDeleted);
        this.specificInfo = specificInfo;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.tel = tel;
        this.userAdditionalInfo = userAdditionalInfo;
    }

    public static Member of(boolean isDeleted, MemberSpecificInfo specificInfo, String email, String pwd, String name, String tel, UserAdditionalInfo userAdditionalInfo) {
        return new Member(isDeleted, specificInfo, email, pwd, name, tel, userAdditionalInfo);
    }

    public static Member createUser(String email, String pwd, String name, String tel, String businessNumber) {
        MemberSpecificInfo userSpecificInfo = MemberSpecificInfo.generateUser();
        UserAdditionalInfo additionalInfo = UserAdditionalInfo.init(businessNumber);
        return of(false, userSpecificInfo, email, pwd, name, tel, additionalInfo);
    }
}
