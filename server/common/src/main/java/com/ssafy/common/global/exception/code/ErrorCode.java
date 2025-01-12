package com.ssafy.common.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    TOKEN_EXPIRED(UNAUTHORIZED, "토큰이 만료되었습니다."),

    MEMBER_NOT_FOUND(NOT_FOUND, "회원을 찾을 수 없습니다."),
    INVALID_MEMBER_ROLE(BAD_REQUEST, "유효하지 않은 회원 권한입니다."),
    INVALID_EMAIL_LENGTH(BAD_REQUEST, "이메일은 최대 100자 입니다."),
    INVALID_EMAIL(BAD_REQUEST, "이메일을 올바르게 입력해주세요."),
    INVALID_PASSWORD_LENGTH(BAD_REQUEST, "비밀번호는 최소 8자에서 최대 20자 입니다."),
    INVALID_PASSWORD(BAD_REQUEST, "비밀번호를 올바르게 입력해주세요."),
    INVALID_NAME_LENGTH(BAD_REQUEST, "이름은 최대 20자 입니다."),
    INVALID_NAME(BAD_REQUEST, "이름을 올바르게 입력해주세요."),
    INVALID_TEL(BAD_REQUEST, "연락처를 올바르게 입력해주세요."),
    INVALID_BUSINESS_NUMBER_LENGTH(BAD_REQUEST, "사업자 번호는 최대 12자 입니다."),
    INVALID_BUSINESS_NUMBER(BAD_REQUEST, "사업자 번호를 올바르게 입력해주세요."),
    INVALID_ACCOUNT_NUMBER_LENGTH(BAD_REQUEST, "은행 계좌는 최대 14자 입니다."),
    INVALID_ACCOUNT_NUMBER(BAD_REQUEST, "은행 계좌를 올바르게 입력해주세요."),
    INVALID_BANK_CODE(BAD_REQUEST, "유효하지 않은 은행 코드입니다."),
    DUPLICATE_EMAIL(BAD_REQUEST, "이미 사용중인 이메일입니다."),
    DUPLICATE_TEL(BAD_REQUEST, "이미 사용중인 연락처입니다."),
    DUPLICATE_BUSINESS_NUMBER(BAD_REQUEST, "이미 사용중인 사업자 번호입니다."),
    NOT_MATCH_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    NOT_CHANGE_TEL(BAD_REQUEST, "현재 사용중인 연락처입니다."),
    HAS_BUSINESS_NUMBER(BAD_REQUEST, "사업자 번호가 등록된 회원입니다."),
    NOT_BUSINESS_MEMBER(BAD_REQUEST, "사업자 회원이 아닙니다."),

    FAIL_SEND_AUTHENTICATION_NUMBER_TO_BANK_ACCOUNT(BAD_REQUEST, "인증 번호 전송을 실패했습니다."),
    AUTHENTICATION_NUMBER_EXPIRED(BAD_REQUEST, "인증 번호가 만료되었습니다."),
    INVALID_AUTHENTICATION_NUMBER(BAD_REQUEST, "인증 번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
