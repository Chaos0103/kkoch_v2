package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.common.exception.LengthOutOfRangeException;
import com.ssafy.user_service.common.exception.NotSupportedException;
import com.ssafy.user_service.common.exception.StringFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberValidateTest {

    @DisplayName("이메일의 길이가 최대 범위를 벗어나면 예외가 발생한다.")
    @Test
    void emailLengthMoreThanMaxLength() {
        //given
        String email = "a".repeat(101);

        //when //then
        assertThatThrownBy(() -> MemberValidate.validateEmail(email))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("이메일의 길이는 최대 100자리 입니다.");
    }

    @DisplayName("이메일의 형식이 맞지 않으면 예외가 발생한다.")
    @CsvSource({"@ssafy.com", "ssafyssafy.com", "ssafy@.com", "ssafy@ssafycom", "ssafy@ssafy.", "ssafy"})
    @ParameterizedTest
    void emailNotMatchesRegex(String email) {
        //given //when //then
        assertThatThrownBy(() -> MemberValidate.validateEmail(email))
            .isInstanceOf(StringFormatException.class)
            .hasMessage("이메일을 올바르게 입력해주세요.");
    }

    @DisplayName("이메일의 유효성을 검증한다.")
    @Test
    void validateEmail() {
        //given
        String email = "a".repeat(90) + "@ssafy.com";

        //when
        boolean result = MemberValidate.validateEmail(email);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("비밀번호의 길이가 최대 범위를 벗어나면 예외가 발생한다.")
    @Test
    void passwordLengthMoreThanMaxLength() {
        //given
        String password = "a".repeat(21);

        //when //then
        assertThatThrownBy(() -> MemberValidate.validatePassword(password))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("비밀번호의 길이는 최소 8자리에서 최대 20자리 입니다.");
    }

    @DisplayName("비밀번호의 길이가 최소 범위를 벗어나면 예외가 발생한다.")
    @Test
    void passwordLengthLessThanMaxLength() {
        //given
        String password = "a".repeat(7);

        //when //then
        assertThatThrownBy(() -> MemberValidate.validatePassword(password))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("비밀번호의 길이는 최소 8자리에서 최대 20자리 입니다.");
    }

    @DisplayName("비밀번호에 영문, 숫자, 특수문자가 모두 포함되지 않았다면 예외가 발생한다.")
    @CsvSource({"ssafyssafy!", "ssafy1234", "12341234!"})
    @ParameterizedTest
    void passwordNotMatchesRegex(String password) {
        //given //when //then
        assertThatThrownBy(() -> MemberValidate.validatePassword(password))
            .isInstanceOf(StringFormatException.class)
            .hasMessage("비밀번호를 올바르게 입력해주세요.");
    }

    @DisplayName("비밀번호의 유효성을 검증한다.")
    @CsvSource({"ssafy12!", "ssafy12345ssafy1234!"})
    @ParameterizedTest
    void validatePassword(String password) {
        //given //when
        boolean result = MemberValidate.validatePassword(password);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("이름의 길이가 최대 범위를 벗어나면 예외가 발생한다.")
    @Test
    void nameLengthMoreThanMaxLength() {
        //given
        String name = "김".repeat(21);

        //when //then
        assertThatThrownBy(() -> MemberValidate.validateName(name))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("이름의 길이는 최대 20자리 입니다.");
    }

    @DisplayName("이름에 한글 이외의 문자가 포함되어 있다면 예외가 발생한다.")
    @Test
    void nameNotMatchesRegex() {
        //given
        String name = "Kim싸피";

        //when //then
        assertThatThrownBy(() -> MemberValidate.validateName(name))
            .isInstanceOf(StringFormatException.class)
            .hasMessage("이름을 올바르게 입력해주세요.");
    }

    @DisplayName("이름의 유효성을 검증한다.")
    @Test
    void validateName() {
        //given
        String name = "김수한무거북이와두루미삼천갑자동방삭치치";

        //when
        boolean result = MemberValidate.validateName(name);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("연락처의 길이가 최대 범위를 벗어나면 예외가 발생한다.")
    @Test
    void telLengthMoreThanMaxLength() {
        //given
        String tel = "010123412345";

        //when //then
        assertThatThrownBy(() -> MemberValidate.validateTel(tel))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("연락처를 올바르게 입력해주세요.");
    }

    @DisplayName("연락처의 길이가 최소 범위를 벗어나면 예외가 발생한다.")
    @Test
    void telLengthLessThanMaxLength() {
        //given
        String tel = "0101234123";

        //when //then
        assertThatThrownBy(() -> MemberValidate.validateTel(tel))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("연락처를 올바르게 입력해주세요.");
    }

    @DisplayName("연락처에 숫자 이외의 문자가 포함되어 있다면 예외가 발생한다.")
    @Test
    void telNotMatchesRegex() {
        //given
        String tel = "010일2341234";

        //when //then
        assertThatThrownBy(() -> MemberValidate.validateTel(tel))
            .isInstanceOf(StringFormatException.class)
            .hasMessage("연락처를 올바르게 입력해주세요.");
    }

    @DisplayName("연락처의 유효성을 검증한다.")
    @Test
    void validateTel() {
        //given
        String tel = "01012341234";

        //when
        boolean result = MemberValidate.validateTel(tel);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("사업자 번호의 길이가 최대 범위를 벗어나면 예외가 발생한다.")
    @Test
    void businessNumberLengthMoreThanMaxLength() {
        //given
        String businessNumber = "1234561234567";

        //when //then
        assertThatThrownBy(() -> MemberValidate.validateBusinessNumber(businessNumber))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("사업자 번호의 길이는 최대 12자리 입니다.");
    }

    @DisplayName("사업자 번호에 숫자 이외의 문자가 포함되어 있다면 예외가 발생한다.")
    @Test
    void businessNumberNotMatchesRegex() {
        //given
        String businessNumber = "123-12-12345";

        //when //then
        assertThatThrownBy(() -> MemberValidate.validateBusinessNumber(businessNumber))
            .isInstanceOf(StringFormatException.class)
            .hasMessage("사업자 번호를 올바르게 입력해주세요.");
    }

    @DisplayName("사업자 번호의 유효성을 검증한다.")
    @Test
    void validateBusinessNumber() {
        //given
        String businessNumber = "123123412345";

        //when
        boolean result = MemberValidate.validateBusinessNumber(businessNumber);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("지원하지 않는 은행 코드라면 예외가 발생한다.")
    @Test
    void bankCodeIsNotSupported() {
        //given
        String bankCode = "000";

        //when //then
        assertThatThrownBy(() -> MemberValidate.validateBankCode(bankCode))
            .isInstanceOf(NotSupportedException.class)
            .hasMessage("지원하지 않는 은행 코드입니다.");
    }

    @DisplayName("은행 코드의 유효성을 검증한다.")
    @CsvSource({"088", "004", "081", "020", "011"})
    @ParameterizedTest
    void validateBankCode(String bankCode) {
        //given //when
        boolean result = MemberValidate.validateBankCode(bankCode);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("은행 계좌의 길이가 최대 범위를 벗어나면 예외가 발생한다.")
    @Test
    void accountNumberLengthMoreThanMaxLength() {
        //given
        String accountNumber = "012345678901234";

        //when //then
        assertThatThrownBy(() -> MemberValidate.validateAccountNumber(accountNumber))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("은행 계좌의 길이는 최대 14자리 입니다.");
    }

    @DisplayName("은행 계좌에 숫자 이외의 문자가 포함되어 있다면 예외가 발생한다.")
    @Test
    void accountNumberNotMatchesRegex() {
        //given
        String accountNumber = "123-123-123456";

        //when //then
        assertThatThrownBy(() -> MemberValidate.validateAccountNumber(accountNumber))
            .isInstanceOf(StringFormatException.class)
            .hasMessage("은행 계좌를 올바르게 입력해주세요.");
    }

    @DisplayName("은행 계좌의 유효성을 검증한다.")
    @Test
    void validateAccountNumber() {
        //given
        String accountNumber = "01234567890123";

        //when
        boolean result = MemberValidate.validateAccountNumber(accountNumber);

        //then
        assertThat(result).isTrue();
    }
}