package com.college.faculty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.RequestScope;

import com.college.faculty.utils.CustomLogger;
import com.college.faculty.utils.RequestId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@SpringBootApplication
public class FacultyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacultyServiceApplication.class, args);
	}

	@Bean
	ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();

		// to support optional feature
		mapper.registerModule(new Jdk8Module());

		return mapper;
	}

	@Bean
	@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	RequestId requestId() {
		return new RequestId();
	}

	@Bean
	CustomLogger logger() {
		return CustomLogger.getLogger("student-service");
	}

}
