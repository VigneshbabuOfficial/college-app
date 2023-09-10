package com.college.departments.dto;

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
public class SuccessResponseDTO implements ResponseDTO {

	private List<?> data = new ArrayList<>();
	@Builder.Default
	private int page = 1;
	@Builder.Default
	private int limit = 10;

	private Long total;
	private int currentPageTotal;

}
