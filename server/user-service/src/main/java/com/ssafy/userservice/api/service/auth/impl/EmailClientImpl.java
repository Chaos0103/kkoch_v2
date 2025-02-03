package com.ssafy.userservice.api.service.auth.impl;

import com.ssafy.userservice.api.service.auth.EmailClient;
import org.springframework.stereotype.Component;

@Component
public class EmailClientImpl implements EmailClient {

    @Override
    public boolean sendAuthenticationNumber(String to, String issuedAuthenticationNumber) {
        return false;
    }
}
