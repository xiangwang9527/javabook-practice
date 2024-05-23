package cn.javabook.chapter15.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis配置类
 * 
 */
@Configuration
public class JedisConfig {
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.max-wait}")
	private long maxWait;

	@Value("${spring.redis.min-idle}")
	private int minIdle;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Bean
	public JedisPool redisPoolFactory() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWait);
		jedisPoolConfig.setMinIdle(minIdle);
		return new JedisPool(jedisPoolConfig, host, port, timeout, password);
	}
}
