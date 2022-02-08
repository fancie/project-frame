package com.hidiu.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author fancie
 * @title: RedisConfiguration
 * @projectName project-frame
 * @description: TODO
 * @date 2022/2/8 下午8:21
 */
@Configuration
public class RedisConfiguration {

    @Resource
    private LettuceConnectionFactory myLettuceConnectionFactory;

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(myLettuceConnectionFactory);
        return template;
    }
}
