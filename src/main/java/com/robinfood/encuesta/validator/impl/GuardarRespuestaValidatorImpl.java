package com.robinfood.encuesta.validator.impl;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.robinfood.encuesta.entity.PreguntaEntity;
import com.robinfood.encuesta.entity.RespuestaPreguntaEntity;
import com.robinfood.encuesta.repository.PreguntaRepository;
import com.robinfood.encuesta.repository.RespuestaPreguntaRepository;
import com.robinfood.encuesta.validator.GuardarRespuestaValidator;

public class GuardarRespuestaValidatorImpl implements ConstraintValidator<GuardarRespuestaValidator, Object> {
	
	@Autowired
	PreguntaRepository preguntaRepository;
	
	@Autowired
	RespuestaPreguntaRepository respuestaPreguntaRepository;
	
	String campo;
	String name;
	
	@Override
	public void initialize(GuardarRespuestaValidator constraintAnnotation) {
        this.campo = constraintAnnotation.campo();
        this.name = constraintAnnotation.name();
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Long campoValor = Long.parseLong(new BeanWrapperImpl(value).getPropertyValue(campo).toString());		
		if (name.toUpperCase().equalsIgnoreCase("PREGUNTA")) {
			if(campoValor == null || campoValor < 1) return false;
			Optional<PreguntaEntity> optEntity = preguntaRepository.findById(campoValor);
			if(!optEntity.isPresent()) {
				context.buildConstraintViolationWithTemplate("El ID de pregunta enviado no existe").addConstraintViolation();
				return false;
			}
		} else if (name.toUpperCase().equalsIgnoreCase("RESPUESTA")) {
			if (campoValor == -1) return true; 
			if(campoValor < -1) return false;
			Optional<RespuestaPreguntaEntity> optEntity = respuestaPreguntaRepository.findById(campoValor);
			if(!optEntity.isPresent()) {
				context.buildConstraintViolationWithTemplate("El ID de respuesta enviado no existe").addConstraintViolation();
				return false;
			}				
		}
		return true;
	}

}
