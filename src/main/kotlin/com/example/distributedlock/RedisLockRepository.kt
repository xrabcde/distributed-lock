package com.example.distributedlock

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisLockRepository(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun lock(key: String) = redisTemplate.opsForValue()
        .setIfAbsent(key, "true", Duration.ofMillis(3_000))

    fun unlock(key: String) = redisTemplate.delete(key)
}
