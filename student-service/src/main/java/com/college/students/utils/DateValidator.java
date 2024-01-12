package com.college.students.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

	String dateFormat = "";

	String dateFormatPattern = "";

	@Override
	public void initialize(ValidDate constraintAnnotation) {

		ConstraintValidator.super.initialize(constraintAnnotation);

		this.dateFormat = constraintAnnotation.format();
		this.dateFormatPattern = constraintAnnotation.formatPattern();
	}

	@SuppressWarnings("unused")
	@Override
	public boolean isValid(String date, ConstraintValidatorContext context) {

		if (CustomStringUtil.isBlank(date))
			return false;

		SimpleDateFormat sdfrmt = new SimpleDateFormat(dateFormat);

		sdfrmt.setLenient(false);

		try {

			Date inputDate = sdfrmt.parse(date);

			final Pattern pattern = Pattern.compile(dateFormatPattern);
			Matcher matcher = pattern.matcher(date);

			return matcher.matches();
		} catch (ParseException e) {
			return false;
		}

	}

}
