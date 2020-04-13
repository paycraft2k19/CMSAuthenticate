package com.paycraft.cmsauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class, MongoAutoConfiguration.class })
@EnableSwagger2
public class CmsAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsAuthenticationApplication.class, args);
	}

}
