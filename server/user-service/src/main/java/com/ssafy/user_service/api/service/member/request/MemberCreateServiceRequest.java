package com.ssafy.user_service.api.service.member.request;

import com.ssafy.user_service.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ssafy.user_service.domain.member.MemberValidate.*;

@Getter
public class MemberCreateServiceRequest {

    private final String email;
    private final String password;
    private final String name;
    private final String tel;
    private final String businessNumber;

    @Builder
    private MemberCreateServiceRequest(String email, String password, String name, String tel, String businessNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
    }

    public static MemberCreateServiceRequest of(String email, String password, String name, String tel, String businessNumber) {
        validateEmail(email);
        validatePassword(password);
        validateName(name);
        validateTel(tel);

        return new MemberCreateServiceRequest(email, password, name, tel, businessNumber);
    }

    public static MemberCreateServiceRequest createUser(String email, String password, String name, String tel, String businessNumber) {
        validateBusinessNumber(businessNumber);
        return of(email, password, name, tel, businessNumber);
    }

    public static MemberCreateServiceRequest createAdmin(String email, String password, String name, String tel) {
        return of(email, password, name, tel, null);
    }

    public Member toEntity(PasswordEncoder encoder) {
        String encodedPwd = encoder.encode(password);

        if (businessNumber == null) {
            return Member.createAdmin(email, encodedPwd, name, tel);
        }
        return Member.createUser(email, encodedPwd, name, tel, businessNumber);
    }
}
