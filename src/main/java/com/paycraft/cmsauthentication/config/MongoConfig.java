package com.paycraft.cmsauthentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {

	@Value("${mongo.url}")
	private String mongoUrl;

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(MongoClients.create(mongoUrl), new MongoClientURI(mongoUrl).getDatabase());
	}
}
