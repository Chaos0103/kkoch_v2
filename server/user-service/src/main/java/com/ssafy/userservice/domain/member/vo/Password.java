package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_PASSWORD;
import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_PASSWORD_LENGTH;
import static com.ssafy.common.global.utils.StringUtils.*;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;

    @Column(nullable = false, columnDefinition = "char(60)", length = 60)
    private String password;

    private Password(String password, PasswordEncoder encoder) {
        validation(password);
        this.password = encoder.encode(password);
    }

    public static Password of(String password, PasswordEncoder encoder) {
        return new Password(password, encoder);
    }

    public boolean matches(String password, PasswordEncoder encoder) {
        return encoder.matches(password, this.password);
    }

    private void validation(String password) {
        if (isLengthLessThan(password, MIN_PASSWORD_LENGTH) || isLengthMoreThan(password, MAX_PASSWORD_LENGTH)) {
            throw MemberException.of(INVALID_PASSWORD_LENGTH);
        }

        if (isNotPasswordPattern(password)) {
            throw MemberException.of(INVALID_PASSWORD);
        }
    }
}
