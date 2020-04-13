package com.paycraft.cmsauthentication.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

	@Autowired
	private RedisTemplate<String, String> template;

	@Value("${redis.enabled}")
	private boolean cacheEnabled;

	public void clearAllCaches() {
		template.execute((RedisCallback<String>) connection -> {
			connection.flushAll();
			return null;
		});
	}

	public void put(String key, String toBeCached, long ttlMinutes) {
		if (!cacheEnabled)
			return;
		template.opsForValue().set(key, toBeCached, ttlMinutes, TimeUnit.DAYS);
	}

	public void put(String key, String toBeCached) {
		if (!cacheEnabled)
			return;
		if (toBeCached == null)
			return;
		put(key, toBeCached, 10);
	}
	public void delete(String deviceId) {
		if (!cacheEnabled)
			return;
		template.delete(deviceId);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		if (!cacheEnabled)
			return null;
		return (T) template.opsForValue().get(key);
	}
}
