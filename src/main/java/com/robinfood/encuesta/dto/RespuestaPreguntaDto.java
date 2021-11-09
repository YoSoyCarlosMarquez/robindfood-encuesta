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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaPreguntaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
    public static final String NAME = "RespuestaPreguntaDto";
    
    private Long id;
        
    private Long pregunta;
    
    @NotNull(message = "Ingrese la descripcion de la respuesta")
    @NotEmpty(message = "Ingrese la descripcion de la respuesta")
    @NotBlank(message = "Ingrese la descripcion de la respuesta")
    @Length(max = 360, message = "La descripcion de la respuesta debe tener máximo 360 caracteres")
    private String descripcion;
    
    @Default
    @Length(max = 1, min = 1, message = "El estado de la respuesta debe ser de un caracter")    
    @Pattern(regexp = "[A,I]", message = "El estado de la respuesta solo debe ser activo (A) o inactivo (I)")
    private String estado = "A";

}
