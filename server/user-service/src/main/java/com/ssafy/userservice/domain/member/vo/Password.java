package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import com.ssafy.userservice.utils.PasswordUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_PASSWORD;
import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_PASSWORD_LENGTH;
import static com.ssafy.common.global.utils.StringUtils.*;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Password {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;

    @Column(nullable = false, columnDefinition = "char(60)", length = 60)
    private final String password;

    private Password(String password) {
        validation(password);
        this.password = PasswordUtils.encode(password);
    }

    public static Password of(String password) {
        return new Password(password);
    }

    public boolean matches(String password) {
        return PasswordUtils.matches(password, this.password);
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
