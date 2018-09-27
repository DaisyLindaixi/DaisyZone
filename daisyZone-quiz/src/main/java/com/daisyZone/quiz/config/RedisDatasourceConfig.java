package com.daisyZone.quiz.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

/**
 * Redis缓存配置
 *
 * @author Luo Liang
 */
@Configuration
public class RedisDatasourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public JedisClientConfiguration jedisClientConfiguration() {
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder configurationBuilder = (
                JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        GenericObjectPoolConfig gopc = new GenericObjectPoolConfig();
        gopc.setMaxIdle(Integer.valueOf(env.getProperty("spring.redis.jedis.pool.max-idle", "1000")));
        gopc.setMaxTotal(Integer.valueOf(env.getProperty("spring.redis.jedis.pool.max-active", "8")));
        gopc.setMaxWaitMillis(Long.valueOf(env.getProperty("spring.redis.jedis.pool.max-wait", "10000")));
        gopc.setMinIdle(Integer.valueOf(env.getProperty("spring.redis.jedis.pool.min-idle", "10")));
        return configurationBuilder.poolConfig(gopc).build();
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory(redisStandaloneConfiguration(), jedisClientConfiguration());
    }

    private JedisConnectionFactory initJedisConnectionFactory(Integer database) {
        return new JedisConnectionFactory(initRedisStandaloneConfiguration(database), jedisClientConfiguration());
    }

    @Bean
    RedisStandaloneConfiguration redisStandaloneConfiguration() {
        return initRedisStandaloneConfiguration(Integer.valueOf(env.getProperty("spring.redis.database", "0")));
    }

    private RedisStandaloneConfiguration initRedisStandaloneConfiguration(Integer database) {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(env.getProperty("spring.redis.host", "127.0.0.1"));
        standaloneConfiguration.setPort(Integer.valueOf(env.getProperty("spring.redis.port", "6379")));
        standaloneConfiguration.setDatabase(database);
        String pwd = env.getProperty("spring.redis.password");
        standaloneConfiguration.setPassword(StringUtils.isEmpty(pwd) ? RedisPassword.none() : RedisPassword.of(pwd));
        return standaloneConfiguration;
    }

    @Bean(name = "stringRedisTemplate")
    public RedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(initJedisConnectionFactory(Integer.valueOf(env.getProperty("spring.redis.database", "0"))));
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(initJedisConnectionFactory(Integer.valueOf(env.getProperty("spring.redis.database", "0"))));
        return redisTemplate;
    }

    @Bean(name = "validControlRedisTemplate")
    public RedisTemplate<String, Object> validControlRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(initJedisConnectionFactory(Integer.valueOf(env.getProperty("spring.redis.security-database", "1"))));
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

}
