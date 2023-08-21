package com.college.students.departments.dto;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.college.students.departments.entity.Department;
import com.college.students.departments.enums.Departments;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DepartmentResponseDTO implements Serializable {

	@Autowired
	private ObjectMapper objectMapper;

	private static final long serialVersionUID = 1L;

	private Long id;

	private Departments name;

	private String comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Departments getName() {
		return name;
	}

	public void setName(Departments name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public DepartmentResponseDTO converter(Department department) {

		return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(department,
				DepartmentResponseDTO.class);
	}

	@Override
	public String toString() {
		return "DepartmentResponseDTO [id=" + id + ", name=" + name + ", comments=" + comments + "]";
	}

}
