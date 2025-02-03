package com.ssafy.userservice.api.service.auth.menager;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class EmailAuthenticationManager {

    private final ValueOperations<String, String> operations;

    public EmailAuthenticationManager(RedisTemplate<String, String> template) {
        this.operations = template.opsForValue();
    }

    public void save(String email, String issuedAuthenticationNumber) {
        operations.set(email, issuedAuthenticationNumber, 5, TimeUnit.MINUTES);
    }

    public Optional<String> findByKey(String email) {
        String authenticationNumber = operations.get(email);
        return Optional.ofNullable(authenticationNumber);
    }

    public void delete(String email) {
        operations.getAndDelete(email);
    }
}
