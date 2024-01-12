package com.college.departments.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class ConverterConfig {

	@Autowired
	private ObjectMapper objectMapper;

	public <T> Object converter(Object source, Class<?> target) {

		return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(source,
				target);
	}

	public <T> List<Object> convertToList(Object source, Class<?> target) {

		return Arrays.asList(objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.convertValue(source, target));
	}

	public <T> List<Object> converterList(List<?> sourceList, Class<?> target) {

		List<Object> outputList = new ArrayList<>();

		for (Object object : sourceList) {
			outputList.add(converter(object, target));
		}

		return outputList;
	}

	public <T> List<Object> convertToObjectNodeList(List<ObjectNode> sourceList, Class<?> target) {

		List<Object> outputList = new ArrayList<>();

		for (Object object : sourceList) {
			outputList.add(converter(object, target));
		}

		return outputList;
	}

}
