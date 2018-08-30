package com.going.storm.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	@Autowired
	private RedisTemplate redisTemplate;

	@Bean
	public RedisTemplate redisTemplateInit() {
		StringRedisSerializer redisSerializer = new StringRedisSerializer();
		// 设置序列化Key的实例化对象
		redisTemplate.setKeySerializer(redisSerializer);
		// 设置序列化Value的实例化对象
		redisTemplate.setValueSerializer(redisSerializer);
        //value hashmap序列化
		redisTemplate.setHashValueSerializer(redisSerializer);
        //key haspmap序列化
		redisTemplate.setHashKeySerializer(redisSerializer);
		return redisTemplate;
	}

	public static void main(String[] args) {
		System.setProperty("user.timezone", "Etc/GMT-8");
		SpringApplication.run(Application.class, args);
	}

}
