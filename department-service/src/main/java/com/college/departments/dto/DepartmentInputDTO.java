package com.college.departments.dto;

import java.io.Serializable;
import java.util.Optional;

import javax.validation.constraints.Pattern;

import com.college.departments.utils.validation.NotBlankForOptional;

public class DepartmentInputDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlankForOptional(message = "Invalid Department name. Accepted values are ME, ECE, EEE, CSE, IT", groups = Create.class)
	private Optional<@Pattern(regexp = "\\bME|ECE|EEE|CSE|IT\\b", message = "Invalid Department name. Accepted values are ME, ECE, EEE, CSE, IT", groups = {
			Create.class, Update.class }) String> name;

	private Optional<String> comments;

	public Optional<String> getName() {
		return name;
	}

	public void setName(Optional<String> name) {
		this.name = name;
	}

	public Optional<String> getComments() {
		return comments;
	}

	public void setComments(Optional<String> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "DepartmentInputDTO [name=" + name + ", comments=" + comments + "]";
	}

}
