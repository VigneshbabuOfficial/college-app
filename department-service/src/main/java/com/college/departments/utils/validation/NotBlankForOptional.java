package com.college.departments.utils.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
@Constraint(validatedBy = { NotBlankForOptionalValidator.class })
public @interface NotBlankForOptional {

	String message() default "must not be blank";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String defaultValue() default "test";
}
