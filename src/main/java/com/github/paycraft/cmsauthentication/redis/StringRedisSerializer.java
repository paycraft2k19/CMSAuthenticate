package com.github.paycraft.cmsauthentication.redis;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;

public class StringRedisSerializer implements RedisSerializer<String> {

	public StringRedisSerializer() {
		this(Charset.forName("UTF8"));
	}

	public StringRedisSerializer(final Charset charset) {
		this.charset = charset;
	}

	private final Charset charset;

	@Override
	public byte[] serialize(String t){
		 return (t == null ? null : t.getBytes(charset));
	}

	@Override
	public String deserialize(byte[] bytes){
		return (bytes == null ? null : String.valueOf(new String(bytes, charset)));
	}

}
