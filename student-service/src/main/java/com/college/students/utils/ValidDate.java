package com.college.students.utils;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target({ FIELD, PARAMETER, TYPE_USE })
@Constraint(validatedBy = { DateValidator.class })
public @interface ValidDate {
	String message() default "Invalid date format";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String format();

	String formatPattern() default "";
}
