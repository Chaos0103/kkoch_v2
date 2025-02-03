package com.ssafy.userservice.api.service.auth;

public interface EmailClient {

    boolean sendAuthenticationNumber(String to, String issuedAuthenticationNumber);
}
