package com.college.departments.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.college.departments.entity.Department;
import com.college.departments.enums.Departments;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class DepartmentResponseDTO implements Serializable {

	@Autowired
	private ObjectMapper objectMapper;

	private static final long serialVersionUID = 1L;

	private Long id;

	private Departments name;

	private String comments;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime updatedAt;

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public DepartmentResponseDTO converter(Department department) {

		return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(department,
				DepartmentResponseDTO.class);
	}

	@Override
	public String toString() {
		return "DepartmentResponseDTO [id=" + id + ", name=" + name + ", comments=" + comments + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
