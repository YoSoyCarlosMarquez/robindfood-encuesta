package com.robinfood.encuesta.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.robinfood.encuesta.entity.PreguntaEntity;
import com.sun.istack.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncuestaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
    public static final String NAME = "EncuestaDto";

    private Long id;
    
    @NotNull(message = "Ingrese un titulo")
    @NotEmpty(message = "Ingrese un titulo")
    @NotBlank(message = "Ingrese un titulo")
    @Length(max = 120, message = "El titulo debe tener máximo 120 caracteres")    
    private String titulo;
    
    @Length(max = 360, message = "La descripción debe tener máximo 360 caracteres")
    private String descripcion;
    
    @NotNull(message = "Ingrese una fecha")    
    @Default
    private LocalDate fecha = LocalDate.now(ZoneId.of("America/Bogota"));
    
    @Default
    @Length(max = 1, min = 1, message = "El estado debe ser de un caracter")    
    @Pattern(regexp = "[A,I]", message = "El estado solo debe ser activo (A) o inactivo (I)")
    private String estado = "A";
    
    @Valid
    private List<PreguntaDto> preguntas;

}
