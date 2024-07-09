package com.kkoch.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected String generateMemberKey() {
        return UUID.randomUUID().toString();
    }
}
