package com.ssafy.board_service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport {

    protected String generateMemberKey() {
        return UUID.randomUUID().toString();
    }
}
