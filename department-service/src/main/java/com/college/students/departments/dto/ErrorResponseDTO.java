package com.college.students.departments.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ErrorResponseDTO implements ResponseDTO, Serializable {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String message;
}
