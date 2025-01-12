package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_TEL;
import static com.ssafy.common.global.utils.StringUtils.isLengthNotEquals;
import static com.ssafy.common.global.utils.StringUtils.isNotNumber;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Tel {

    private static final int TEL_LENGTH = 11;

    @Column(unique = true, nullable = false, columnDefinition = "char(11)", length = TEL_LENGTH)
    private final String tel;

    private Tel(String tel) {
        validation(tel);
        this.tel = tel;
    }

    public static Tel of(String tel) {
        return new Tel(tel);
    }

    @Override
    public String toString() {
        return tel;
    }

    private void validation(String tel) {
        if (isLengthNotEquals(tel, TEL_LENGTH)) {
            throw MemberException.of(INVALID_TEL);
        }

        if (isNotNumber(tel)) {
            throw MemberException.of(INVALID_TEL);
        }
    }
}
