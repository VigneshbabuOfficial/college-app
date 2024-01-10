package com.college.students.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConverterConfig {

	@Autowired
	private ObjectMapper objectMapper;

	public <T> List<Object> converterList(Object source, Class<?> target) {

		return Arrays.asList(objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.convertValue(source, target));
	}
}
