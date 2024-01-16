package com.college.faculty.dto;

import java.io.Serializable;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern.Flag;

import com.college.faculty.utils.ErrorCodeMessage;
import com.college.faculty.utils.NotBlankForOptional;
import com.college.faculty.utils.ValidDate;
import com.college.faculty.utils.ValidString;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FacultyInputDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlankForOptional(message = "Student name is mandatory and must not be empty", groups = Create.class)
	private Optional<@Size(min = 3, max = 255, message = "Student name is mandatory and the length should be within 255", groups = {
			Create.class,
			Update.class }) @ValidString(message = "Student name must not be empty", groups = Update.class) String> name;

	@NotBlankForOptional(message = "Father name is mandatory and must not be empty", groups = Create.class)
	private Optional<@Size(min = 3, max = 255, message = "Father name is mandatory and the length should be within 255", groups = {
			Create.class,
			Update.class }) @ValidString(message = "Father name must not be empty", groups = Update.class) String> fatherName;

	@NotBlankForOptional(message = "Address is mandatory and must not be empty", groups = Create.class)
	private Optional<@ValidString(message = "Address must not be empty", groups = Update.class) String> address;

	@NotBlankForOptional(message = "Adhaar number is mandatory and must be a 12-digit number.", groups = {
			Create.class })
	private Optional<@Size(min = 12, max = 12, message = "Adhaar number must be a 12-digit number", groups = {
			Create.class,
			Update.class }) @ValidString(message = "Adhaar number must not be empty", groups = Update.class) String> adhaarNum;

	@NotBlankForOptional(message = "Date of Birth is mandatory and Please use the format yyyy-MM-dd.", groups = {
			Create.class })
	private Optional<@ValidDate(format = "yyyy-MM-dd", formatPattern = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", groups = {
			Create.class,
			Update.class }, message = "Invalid date format. Please use the format yyyy-MM-dd.") String> dob;

	@NotBlankForOptional(message = "Contact number is mandatory and must not be empty", groups = Create.class)
	private Optional<@Size(min = 8, max = 15, message = "Contact number is mandatory and the length should be minimum 8 and maximum 15 digits", groups = {
			Create.class,
			Update.class }) @ValidString(message = "Contact number must not be empty", groups = Update.class) String> contactNum;

	@NotBlankForOptional(message = "Email is mandatory and must not be empty", groups = Create.class)
	private Optional<@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Flag.CASE_INSENSITIVE, message = ErrorCodeMessage.SORRY_INVALID_EMAIL_MSG, groups = {
			Create.class, Update.class }) String> email;

	private Optional<String> comments;

}
