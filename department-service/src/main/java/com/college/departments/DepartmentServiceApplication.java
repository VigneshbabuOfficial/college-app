package com.college.departments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.RequestScope;

import com.college.departments.utils.CustomLogger;
import com.college.departments.utils.RequestId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@SpringBootApplication
public class DepartmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepartmentServiceApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();

		// to support optional feature
		mapper.registerModule(new Jdk8Module());

		return mapper;
	}

	@Bean
	@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	public RequestId requestId() {
		return new RequestId();
	}

	@Bean
	public CustomLogger logger() {
		return CustomLogger.getLogger("department-service");
	}

}
