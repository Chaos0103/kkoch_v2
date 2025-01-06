package com.ssafy.user_service.common.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository<K, V> {

    private final ValueOperations<K, V> operations;

    public RedisRepository(RedisTemplate<K, V> template) {
        this.operations = template.opsForValue();
    }

    public void save(K key, V value) {
        operations.set(key, value);
    }

    public void save(K key, V value, long timeout, TimeUnit unit) {
        operations.set(key, value, timeout, unit);
    }

    public V findByKey(K key) {
        return operations.get(key);
    }

    public void remove(K key) {
        operations.getAndDelete(key);
    }
}
