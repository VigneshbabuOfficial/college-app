package com.college.departments.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.college.departments.dto.ErrorResponseDTO;
import com.college.departments.dto.ErrorResponsesDTO;

public class CommonUtil {

	private CommonUtil() {
		super();
	}

	public static ErrorResponsesDTO buildBindingResultErrors(BindingResult bindingResult, String requestId) {

		List<ErrorResponseDTO> errors = new ArrayList<>();

		for (ObjectError errorObj : bindingResult.getAllErrors()) {
			errors.add(ErrorResponseDTO.builder().errorCode(ErrorCodeMessage.INVALID_DATA)
					.message(errorObj.getDefaultMessage()).build());
		}

		return ErrorResponsesDTO.builder().errors(errors).requestId(requestId).build();
	}

	public static ErrorResponsesDTO buildErrorResponse(String errorCode, String message, String requestId) {
		return ErrorResponsesDTO.builder()
				.errors(Arrays.asList(ErrorResponseDTO.builder().errorCode(errorCode).message(message).build()))
				.requestId(requestId).build();
	}

}
