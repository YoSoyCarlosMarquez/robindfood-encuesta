package com.robinfood.encuesta.dto;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.sun.istack.Nullable;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class TipoPreguntaDto implements Serializable {
		
	private static final long serialVersionUID = 1L;
    public static final String NAME = "TipoPreguntaDto";
    
    @NotNull(message = "El ID de tipo de pregunta no puede ser nulo o vacio")      
    @Min(value = 1, message = "El ID de tipo de pregunta solo puede ser 1=ABIERTA, 2=UNICA")
    @Max(value = 3, message = "El ID de tipo de pregunta solo puede ser 1=ABIERTA, 2=UNICA")
    private Long id;
    
    @Nullable
    @Default
    private String nombre = "";
    
    @Nullable
    @Default
    private String estado = "A";

}
