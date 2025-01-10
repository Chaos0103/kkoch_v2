package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_NAME;
import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_NAME_LENGTH;
import static com.ssafy.common.global.utils.StringUtils.isLengthMoreThan;
import static com.ssafy.common.global.utils.StringUtils.isNotKorean;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Name {

    private static final int MAX_NAME_LENGTH = 20;

    @Column(nullable = false, updatable = false, length = MAX_NAME_LENGTH)
    private final String name;

    private Name(String name) {
        validation(name);
        this.name = name;
    }

    public static Name of(String name) {
        return new Name(name);
    }

    private void validation(String name) {
        if (isLengthMoreThan(name, MAX_NAME_LENGTH)) {
            throw MemberException.of(INVALID_NAME_LENGTH);
        }

        if (isNotKorean(name)) {
            throw MemberException.of(INVALID_NAME);
        }
    }
}
