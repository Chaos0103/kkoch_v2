package com.ssafy.userservice.api.service.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Masking {

    private static final String MASKING_CHAR = "*";
    private static final int EMAIL_FRONT_PUBLIC_LENGTH = 2;
    private static final int TEL_FRONT_PUBLIC_LENGTH = 3;
    private static final int TEL_BACK_PUBLIC_LENGTH = 4;
    private static final int ACCOUNT_NUMBER_FRONT_PUBLIC_LENGTH = 3;
    private static final int ACCOUNT_NUMBER_BACK_PUBLIC_LENGTH = 6;
    private static final int BUSINESS_NUMBER_FRONT_PUBLIC_LENGTH = 3;
    private static final int BUSINESS_NUMBER_BACK_PUBLIC_LENGTH = 2;

    public static String maskingEmail(String email) {
        String[] splitEmail = email.split("@");

        String id = splitEmail[0];
        String domain = splitEmail[1];

        String front = createFrontSubstringBy(id, EMAIL_FRONT_PUBLIC_LENGTH);
        String masking = createMaskingStr(id, EMAIL_FRONT_PUBLIC_LENGTH);

        return front + masking + "@" + domain;
    }

    public static String maskingTel(String tel) {
        String front = createFrontSubstringBy(tel, TEL_FRONT_PUBLIC_LENGTH);
        String back = createBackSubstringBy(tel, TEL_BACK_PUBLIC_LENGTH);

        String masking = createMaskingStr(tel, TEL_FRONT_PUBLIC_LENGTH + TEL_BACK_PUBLIC_LENGTH);

        return concat(front, masking, back);
    }

    public static String maskingAccountNumber(String accountNumber) {
        String front = createFrontSubstringBy(accountNumber, ACCOUNT_NUMBER_FRONT_PUBLIC_LENGTH);
        String back = createBackSubstringBy(accountNumber, ACCOUNT_NUMBER_BACK_PUBLIC_LENGTH);

        String masking = createMaskingStr(accountNumber, ACCOUNT_NUMBER_FRONT_PUBLIC_LENGTH + ACCOUNT_NUMBER_BACK_PUBLIC_LENGTH);

        return concat(front, masking, back);
    }

    public static String maskingBusinessNumber(String businessNumber) {
        String front = createFrontSubstringBy(businessNumber, BUSINESS_NUMBER_FRONT_PUBLIC_LENGTH);
        String back = createBackSubstringBy(businessNumber, BUSINESS_NUMBER_BACK_PUBLIC_LENGTH);

        String masking = createMaskingStr(businessNumber, BUSINESS_NUMBER_FRONT_PUBLIC_LENGTH + BUSINESS_NUMBER_BACK_PUBLIC_LENGTH);

        return concat(front, masking, back);
    }

    private static String createFrontSubstringBy(String str, int length) {
        return str.substring(0, length);
    }

    private static String createBackSubstringBy(String str, int length) {
        return str.substring(str.length() - length);
    }

    private static String createMaskingStr(String str, int excludedLength) {
        return MASKING_CHAR.repeat(str.length() - excludedLength);
    }

    private static String concat(String front, String masking, String back) {
        return front + masking + back;
    }
}
