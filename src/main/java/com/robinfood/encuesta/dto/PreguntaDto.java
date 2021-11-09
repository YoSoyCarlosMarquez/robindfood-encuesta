package com.robinfood.encuesta.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
    public static final String NAME = "PreguntaDto";
		
    
    private Long id;
    
    private Long encuesta;
    
    @NotNull(message = "Ingrese la pregunta")
    @NotEmpty(message = "Ingrese la pregunta")
    @NotBlank(message = "Ingrese la pregunta")
    @Length(max = 360, message = "La pregunta debe tener máximo 360 caracteres")
    private String pregunta;
    
    @Valid
    private TipoPreguntaDto tipo;
    
    @Default
    @Length(max = 1, min = 1, message = "El estado debe ser de un caracter")    
    @Pattern(regexp = "[A,I]", message = "El estado de la pregunta solo debe ser activo (A) o inactivo (I)")
    private String estado = "A";
    
    @Valid
    private List<RespuestaPreguntaDto> respuestas;

}
