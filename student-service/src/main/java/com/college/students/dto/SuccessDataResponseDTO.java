package com.college.students.dto;

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
public class SuccessDataResponseDTO implements ResponseDTO {

	@Builder.Default
	private List<?> data = new ArrayList<>();
	@Builder.Default
	private int page = 1;
	@Builder.Default
	private int limit = 10;

	private Long total;
	private int currentPageTotal;

}
