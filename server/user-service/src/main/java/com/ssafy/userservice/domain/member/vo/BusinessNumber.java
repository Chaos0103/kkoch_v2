package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_BUSINESS_NUMBER;
import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_BUSINESS_NUMBER_LENGTH;
import static com.ssafy.common.global.utils.StringUtils.*;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class BusinessNumber {

    private static final int MAX_BUSINESS_NUMBER_LENGTH = 12;

    @Column(unique = true, length = MAX_BUSINESS_NUMBER_LENGTH)
    private final String businessNumber;

    private BusinessNumber(String businessNumber) {
        validation(businessNumber);
        this.businessNumber = businessNumber;
    }

    public static BusinessNumber of(String businessNumber) {
        return new BusinessNumber(businessNumber);
    }

    private void validation(String businessNumber) {
        if (isBlank(businessNumber) || isLengthMoreThan(businessNumber, MAX_BUSINESS_NUMBER_LENGTH)) {
            throw MemberException.of(INVALID_BUSINESS_NUMBER_LENGTH);
        }

        if (isNotNumber(businessNumber)) {
            throw MemberException.of(INVALID_BUSINESS_NUMBER);
        }
    }
}
