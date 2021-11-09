package com.robinfood.encuesta.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.robinfood.encuesta.validator.impl.GuardarRespuestaValidatorImpl;

@Documented
@Constraint(validatedBy = GuardarRespuestaValidatorImpl.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GuardarRespuestaValidator {
	
	String message();
	
	String campo();
	
	String name();
	
	int min() default 1;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	@Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
		GuardarRespuestaValidator[] value();
    }
}