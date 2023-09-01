package com.college.departments.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class SuccessResponseDTO implements ResponseDTO {

	private Set<?> data = new HashSet<>();
	@Builder.Default
	private int page = 1;
	@Builder.Default
	private int limit = 10;

}
