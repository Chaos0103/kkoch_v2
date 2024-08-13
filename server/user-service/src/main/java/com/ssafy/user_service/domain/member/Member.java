package com.ssafy.user_service.domain.member;

import com.ssafy.user_service.domain.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ssafy.user_service.api.service.member.Masking.maskingEmail;

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

    public static Member createAdmin(String email, String pwd, String name, String tel) {
        MemberSpecificInfo adminSpecificInfo = MemberSpecificInfo.generateAdmin();
        return of(false, adminSpecificInfo, email, pwd, name, tel, null);
    }

    public void modifyPassword(String pwd) {
        this.pwd = pwd;
    }

    public void modifyTel(String tel) {
        this.tel = tel;
    }

    public void modifyBankAccount(String bankCode, String accountNumber) {
        this.userAdditionalInfo = userAdditionalInfo.withBankAccount(bankCode, accountNumber);
    }

    public boolean isMatchesPwd(PasswordEncoder encoder, String pwd) {
        return encoder.matches(pwd, this.pwd);
    }

    public boolean isNotMatchesPwd(PasswordEncoder encoder, String pwd) {
        return !isMatchesPwd(encoder, pwd);
    }

    public String getMemberKey() {
        return specificInfo.getMemberKey();
    }

    public String getMaskingEmail() {
        return maskingEmail(email);
    }
}
