package com.ssafy.user_service.domain.member;

import com.ssafy.user_service.domain.TimeBaseEntity;
import com.ssafy.user_service.domain.member.vo.BankAccount;
import com.ssafy.user_service.domain.member.vo.MemberSpecificInfo;
import com.ssafy.user_service.domain.member.vo.Role;
import com.ssafy.user_service.domain.member.vo.UserAdditionalInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    public static Member create(Role role, String email, String pwd, String name, String tel) {
        MemberSpecificInfo memberSpecificInfo = MemberSpecificInfo.create(role);
        return of(false, memberSpecificInfo, email, pwd, name, tel, null);
    }

    public UserDetails toUser() {
        return User.builder()
            .username(getMemberKey())
            .password(pwd)
            .roles(getRoleToStr())
            .build();
    }

    public void registerBusinessNumber(String businessNumber) {
        userAdditionalInfo = UserAdditionalInfo.create(businessNumber);
        specificInfo = specificInfo.withRoleBusiness();
    }

    public void modifyPassword(String pwd) {
        this.pwd = pwd;
    }

    public void modifyTel(String tel) {
        this.tel = tel;
    }

    public BankAccount modifyBankAccount(String bankCode, String accountNumber) {
        this.userAdditionalInfo = userAdditionalInfo.withBankAccount(bankCode, accountNumber);
        return userAdditionalInfo.getBankAccount();
    }

    public boolean isMatchesPwd(PasswordEncoder encoder, String pwd) {
        return encoder.matches(pwd, this.pwd);
    }

    public boolean isNotMatchesPwd(PasswordEncoder encoder, String pwd) {
        return !isMatchesPwd(encoder, pwd);
    }

    public boolean isDelete() {
        return getIsDeleted();
    }

    public boolean hasBusinessNumber() {
        return userAdditionalInfo != null;
    }

    public String getMemberKey() {
        return specificInfo.getMemberKey();
    }

    public String getBusinessNumber() {
        return userAdditionalInfo.getBusinessNumber();
    }

    public String getMaskingEmail() {
        return maskingEmail(email);
    }

    private String getRoleToStr() {
        return specificInfo.getRole().toString();
    }
}
