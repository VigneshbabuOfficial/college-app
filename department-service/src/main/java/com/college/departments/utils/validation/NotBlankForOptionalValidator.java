package com.college.departments.utils.validation;

import java.util.Objects;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.college.departments.utils.CustomStringUtil;

public class NotBlankForOptionalValidator implements ConstraintValidator<NotBlankForOptional, Optional<String>> {

	@Override
	public boolean isValid(Optional<String> value, ConstraintValidatorContext context) {
		if (Objects.isNull(value) || value.isEmpty() || (value.isPresent() && CustomStringUtil.isBlank(value.get()))) {
			return false;
		} else {
			return true;
		}
	}

}
