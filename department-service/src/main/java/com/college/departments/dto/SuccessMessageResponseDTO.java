package com.college.departments.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class SuccessMessageResponseDTO implements ResponseDTO {

	private String message;

}
