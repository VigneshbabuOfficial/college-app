package com.college.faculty.utils;

import java.util.Objects;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankForOptionalValidator implements ConstraintValidator<NotBlankForOptional, Optional<?>> {

	@Override
	public boolean isValid(Optional<?> value, ConstraintValidatorContext context) {
		if (Objects.isNull(value) || value.isEmpty()
				|| (value.isPresent() && CustomStringUtil.isBlank(value.get().toString()))) {
			return false;
		} else {
			return true;
		}
	}

}
