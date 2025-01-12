package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import com.ssafy.userservice.api.service.member.Masking;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_EMAIL_LENGTH;
import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_EMAIL;
import static com.ssafy.common.global.utils.StringUtils.isLengthMoreThan;
import static com.ssafy.common.global.utils.StringUtils.isNotEmailPattern;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Email {

    private static final int MAX_EMAIL_LENGTH = 100;

    @Column(unique = true, nullable = false, updatable = false, length = MAX_EMAIL_LENGTH)
    private final String email;

    private Email(String email) {
        validation(email);
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    public String getMasking() {
        return Masking.maskingEmail(email);
    }

    @Override
    public String toString() {
        return email;
    }

    private void validation(String email) {
        if (isLengthMoreThan(email, MAX_EMAIL_LENGTH)) {
            throw MemberException.of(INVALID_EMAIL_LENGTH);
        }

        if (isNotEmailPattern(email)) {
            throw MemberException.of(INVALID_EMAIL);
        }
    }
}
