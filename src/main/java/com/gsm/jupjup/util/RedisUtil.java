package com.gsm.jupjup.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 데이터 가져오기
     * @param key 키
     * @return String => 가져온 값
     */
    public String getData(String key){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 데이터 수정
     * @param key 키
     * @param value 값
     */
    public void setData(String key, String value){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    /**
     * 데이터 유효기간 설정하기
     * @param key 키
     * @param value 값
     * @param duration 유효 기간
     */
    public void setDataExpire(String key,String value,long duration){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key,value,expireDuration);
    }

    /**
     * 데이터 삭제 하기
     * @param key 키
     */
    public void deleteData(String key){
        stringRedisTemplate.delete(key);
    }
}
