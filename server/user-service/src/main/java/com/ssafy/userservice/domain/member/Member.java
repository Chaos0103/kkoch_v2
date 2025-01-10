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
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private Member(boolean isDeleted, MemberSpecificInfo specificInfo, String email, String password, String name, String tel, UserAdditionalInfo userAdditionalInfo, PasswordEncoder encoder) {
        super(isDeleted);
        this.specificInfo = specificInfo;
        this.email = Email.of(email);
        this.password = Password.of(password, encoder);
        this.name = Name.of(name);
        this.tel = Tel.of(tel);
        this.userAdditionalInfo = userAdditionalInfo;
    }

    public static Member of(boolean isDeleted, MemberSpecificInfo specificInfo, String email, String pwd, String name, String tel, UserAdditionalInfo userAdditionalInfo, PasswordEncoder encoder) {
        return new Member(isDeleted, specificInfo, email, pwd, name, tel, userAdditionalInfo, encoder);
    }

    public static Member create(Role role, String email, String pwd, String name, String tel, PasswordEncoder encoder) {
        MemberSpecificInfo memberSpecificInfo = MemberSpecificInfo.create(role);
        return of(false, memberSpecificInfo, email, pwd, name, tel, null, encoder);
    }

    public UserDetails toUser() {
        return User.builder()
            .username(getMemberKey())
            .password(password.getPassword())
            .roles(getRoleToStr())
            .build();
    }

    public void registerBusinessNumber(BusinessNumber businessNumber) {
        userAdditionalInfo = UserAdditionalInfo.create(businessNumber.getBusinessNumber());
        specificInfo = specificInfo.withRoleBusiness();
    }

    public void modifyPassword(String pwd, PasswordEncoder encoder) {
        this.password = Password.of(pwd, encoder);
    }

    public void modifyTel(Tel tel) {
        this.tel = tel;
    }

    public BankAccount modifyBankAccount(String bankCode, String accountNumber) {
        this.userAdditionalInfo = userAdditionalInfo.withBankAccount(bankCode, accountNumber);
        return userAdditionalInfo.getBankAccount();
    }

    public boolean isNotMatchesPwd(String password, PasswordEncoder encoder) {
        return !this.password.matches(password, encoder);
    }

    public boolean telEquals(Tel tel) {
        return this.tel.equals(tel);
    }

    public boolean isWithdraw() {
        return getIsDeleted();
    }

    public boolean hasBusinessNumber() {
        return userAdditionalInfo != null;
    }

    public String getMemberKey() {
        return specificInfo.getMemberKey();
    }

    public String getName() {
        return name.getName();
    }

    public String getTel() {
        return tel.getTel();
    }

    public String getBusinessNumber() {
        return userAdditionalInfo.getBusinessNumber().getBusinessNumber();
    }

    public String getMaskingEmail() {
        return email.getMasking();
    }

    public BankAccount getBankAccount() {
        return userAdditionalInfo.getBankAccount();
    }

    private String getRoleToStr() {
        return specificInfo.getRole().toString();
    }

    public boolean isNotBusiness() {
        return userAdditionalInfo == null || specificInfo.getRole() != Role.BUSINESS;
    }
}
