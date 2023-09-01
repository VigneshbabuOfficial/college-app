package com.college.departments.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConverterConfig {

	@Autowired
	private ObjectMapper objectMapper;

	public <T> Object converter(Object source, Class<?> target) {

		return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(source,
				target);
	}

}
