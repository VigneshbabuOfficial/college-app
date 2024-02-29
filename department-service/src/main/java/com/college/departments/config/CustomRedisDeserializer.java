package com.college.departments.config;

import org.jboss.logging.Logger;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomRedisDeserializer implements RedisSerializer<Object> {

	Logger log = Logger.getLogger(CustomRedisDeserializer.class);

	private ObjectMapper om = new ObjectMapper();

	@Override
	public byte[] serialize(Object t) throws SerializationException {
		try {
			return om.writeValueAsBytes(t);
		} catch (JsonProcessingException e) {
			throw new SerializationException(e.getMessage(), e);
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null) {
			return null;
		}

		try {
			return om.readValue(bytes, Object.class);
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return null;
	}
}
