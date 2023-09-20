package com.college.departments.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.college.departments.controller.DepartmentController;
import com.college.departments.dto.DepartmentInputDTO;
import com.college.departments.dto.ResponseDTO;
import com.college.departments.dto.SuccessDataResponseDTO;
import com.college.departments.service.DepartmentService;
import com.college.departments.utils.CustomLogger;
import com.college.departments.utils.RequestId;

import lombok.SneakyThrows;

/*************** Unit test for @DepartmentController *****************/

class DepartmentControllerTest {

	private CustomLogger log;

	private DepartmentController controller;

	private DepartmentService service;

	private RequestId requestId;

	@BeforeEach
	void setup() {
		log = mock(CustomLogger.class);
		service = mock(DepartmentService.class);
		requestId = mock(RequestId.class);
		controller = new DepartmentController(log, service, requestId);

	}

//	@Test
	void testName() {

		// inputs

		// mock methods

		// expected response

		// assert the results

	}

	@Test
	@SneakyThrows
	void addDepartment_should_create_new_department() {

		// inputs
		DepartmentInputDTO inputs = new DepartmentInputDTO();
		inputs.setName(Optional.of("TEST NAME"));
		inputs.setComments(Optional.of("TEST COMMENT"));
		BindingResult bindingResult = mock(BindingResult.class);

		// expected response
		ResponseDTO responseDTO = SuccessDataResponseDTO.builder().data(Arrays.asList(inputs)).build();
		ResponseEntity<ResponseDTO> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(responseDTO);

		// mock methods
		when(service.addDepartment(any())).thenReturn(expectedResponse);

		// assert the results
		ResponseEntity<ResponseDTO> response = controller.addDepartment(inputs, bindingResult);
		assertThat(response).isEqualTo(expectedResponse);
	}

}
