package com.college.students.departments.dto;

import java.util.HashSet;
import java.util.Set;

public class ResponseDTO {

	private Set<?> data = new HashSet<>();

	private int page = 1;
	private int limit = 10;

	public ResponseDTO(Set<?> data) {
		super();
		this.data = data;
	}

	public Set<?> getData() {
		return data;
	}

	public void setData(Set<?> data) {
		this.data = data;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "ResponseDTO [data=" + data + ", page=" + page + ", limit=" + limit + "]";
	}

}
