package com.ssafy.user_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport {

    @Autowired
    protected BCryptPasswordEncoder passwordEncoder;

    protected String generateMemberKey() {
        return UUID.randomUUID().toString();
    }
}
