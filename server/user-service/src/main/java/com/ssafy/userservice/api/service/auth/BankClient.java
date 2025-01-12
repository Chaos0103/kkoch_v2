package com.ssafy.userservice.api.service.auth;

public interface BankClient {

    boolean sendAuthenticationNumber(String bankCode, String accountNumber, String issuedAuthenticationNumber);
}
