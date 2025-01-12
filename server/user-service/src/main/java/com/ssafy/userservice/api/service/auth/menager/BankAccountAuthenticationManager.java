package com.ssafy.userservice.api.service.auth.menager;

import com.ssafy.userservice.api.service.auth.vo.BankAccountAuthentication;
import com.ssafy.userservice.domain.member.vo.BankAccount;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class BankAccountAuthenticationManager {

    private final ValueOperations<String, BankAccountAuthentication> operations;

    public BankAccountAuthenticationManager(RedisTemplate<String, BankAccountAuthentication> template) {
        this.operations = template.opsForValue();
    }

    public void save(String memberKey, BankAccount bankAccount, String issuedAuthenticationNumber) {
        BankAccountAuthentication bankAccountAuthentication = BankAccountAuthentication.of(issuedAuthenticationNumber, bankAccount);
        operations.set(memberKey, bankAccountAuthentication, 5, TimeUnit.MINUTES);
    }

    public Optional<BankAccountAuthentication> findByKey(String memberKey) {
        BankAccountAuthentication bankAccountAuthentication = operations.get(memberKey);
        return Optional.ofNullable(bankAccountAuthentication);
    }

    public void delete(String memberKey) {
        operations.getAndDelete(memberKey);
    }
}
