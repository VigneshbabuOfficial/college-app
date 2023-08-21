package com.college.students.departments.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.college.students.departments.enums.Departments;

public class DepartmentInputDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Department name is mandatory and must not be blank")
	private Departments name;

	private String comments;

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

	@Override
	public String toString() {
		return "DepartmentInputDTO [name=" + name + ", comments=" + comments + "]";
	}

}
