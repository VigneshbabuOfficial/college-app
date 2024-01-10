package com.college.students.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@DynamicUpdate
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SequenceGenerator(sequenceName = "student_seq", name = "student_seq", allocationSize = 1)
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
	@Column(name = "id")
	private Long id;

	@Column(name = "name", columnDefinition = "character varying(255)")
	private String name;

	@Column(name = "father_name", columnDefinition = "character varying(255)")
	private String fatherName;

	@Column(name = "address", columnDefinition = "character varying(255)")
	private String address;

	@Column(name = "adhaar_num", length = 12, unique = true)
	private Long adhaarNum;

	/*
	 * @JsonSerialize(using = LocalDateSerializer.class)
	 * 
	 * @JsonDeserialize(using = LocalDateDeserializer.class)
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dob")
	private LocalDate dob;

	@Column(name = "contact_num", columnDefinition = "character varying(255)")
	private String contactNum;

	@Column(name = "email", columnDefinition = "character varying(255)")
	private String email;

	@Column(name = "profile_pic")
	private byte[] profilePic;

	@Column(name = "comments", columnDefinition = "character varying(255)")
	private String comments;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@Column(name = "created_at")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@Column(name = "updated_at")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime updatedAt;

}
