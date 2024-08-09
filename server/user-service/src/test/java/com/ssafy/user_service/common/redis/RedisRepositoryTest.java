package com.ssafy.user_service.common.redis;

import com.ssafy.user_service.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class RedisRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private RedisRepository<String, String> redisRepository;

    @DisplayName("레디스에 데이터를 저장하고 조회한다.")
    @Test
    void save() {
        //given
        String key = "key";
        String value = "value";

        //when
        redisRepository.save(key, value);

        //then
        String findValue = redisRepository.findByKey(key);
        assertThat(findValue).isEqualTo(value);
    }
}