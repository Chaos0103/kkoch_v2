package com.ssafy.userservice.api.service.auth.impl;

import com.ssafy.userservice.api.service.auth.BankClient;
import org.springframework.stereotype.Component;

@Component
public class BankClientImpl implements BankClient {

    @Override
    public boolean sendAuthenticationNumber(String bankCode, String accountNumber, String issuedAuthenticationNumber) {
        return false;
    }
}
