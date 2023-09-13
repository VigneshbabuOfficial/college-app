package com.college.departments.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ErrorResponsesDTO implements ResponseDTO, Serializable {

	private static final long serialVersionUID = 1L;

	@Builder.Default
	private List<ErrorResponseDTO> errors = new ArrayList<>();
	private String requestId;
}
