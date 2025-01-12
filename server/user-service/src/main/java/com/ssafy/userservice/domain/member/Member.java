package com.ssafy.userservice.domain.member;

import com.ssafy.common.domain.TimeBaseEntity;
import com.ssafy.userservice.domain.member.enums.Role;
import com.ssafy.userservice.domain.member.vo.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

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

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private Name name;

    @Embedded
    private Tel tel;

    @Embedded
    private UserAdditionalInfo userAdditionalInfo;

    @Builder
    private Member(boolean isDeleted, MemberSpecificInfo specificInfo, Email email, Password password, Name name, Tel tel, UserAdditionalInfo userAdditionalInfo) {
        super(isDeleted);
        this.specificInfo = specificInfo;
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.userAdditionalInfo = userAdditionalInfo;
    }

    public static Member create(Role role, Email email, Password password, Name name, Tel tel) {
        return Member.builder()
            .isDeleted(false)
            .specificInfo(MemberSpecificInfo.create(role))
            .email(email)
            .password(password)
            .name(name)
            .tel(tel)
            .userAdditionalInfo(null)
            .build();
    }

    public UserDetails toUser() {
        return User.builder()
            .username(getMemberKey())
            .password(password.getPassword())
            .roles(getRoleToStr())
            .build();
    }

    public void registerBusinessNumber(BusinessNumber businessNumber) {
        userAdditionalInfo = UserAdditionalInfo.create(businessNumber);
        specificInfo = specificInfo.withRoleBusiness();
    }

    public void modifyPassword(Password password) {
        this.password = password;
    }

    public void modifyTel(Tel tel) {
        this.tel = tel;
    }

    public BankAccount modifyBankAccount(String bankCode, String accountNumber) {
        this.userAdditionalInfo = userAdditionalInfo.withBankAccount(bankCode, accountNumber);
        return userAdditionalInfo.getBankAccount();
    }

    public boolean isNotMatchesPwd(String password) {
        return !this.password.matches(password);
    }

    public boolean telEquals(Tel tel) {
        return this.tel.equals(tel);
    }

    public boolean isBusiness() {
        return userAdditionalInfo != null && specificInfo.getRole() == Role.BUSINESS;
    }

    public boolean isNotBusiness() {
        return !isBusiness();
    }

    public boolean isWithdraw() {
        return getIsDeleted();
    }

    public String getMemberKey() {
        return specificInfo.getMemberKey();
    }

    public String getMaskingEmail() {
        return email.getMasking();
    }

    public String getName() {
        return name.getName();
    }

    public String getTel() {
        return tel.getTel();
    }

    public String getBusinessNumber() {
        return userAdditionalInfo.getBusinessNumber();
    }

    public BankAccount getBankAccount() {
        return userAdditionalInfo.getBankAccount();
    }

    private String getRoleToStr() {
        return specificInfo.getRole().toString();
    }
}
