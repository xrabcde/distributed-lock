package com.example.distributedlock

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    val redisHost: String,

    @Value("\${spring.data.redis.port}")
    val redisPort: Int
) {
    @Bean
    fun redisConnectionFactory() = LettuceConnectionFactory(redisHost, redisPort)

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.connectionFactory = redisConnectionFactory()
        return redisTemplate
    }
}
