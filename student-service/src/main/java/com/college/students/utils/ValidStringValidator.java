package com.college.students.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidStringValidator implements ConstraintValidator<ValidString, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (CustomStringUtil.isBlank(value)) {
			return false;
		} else {
			return true;
		}
	}

}
