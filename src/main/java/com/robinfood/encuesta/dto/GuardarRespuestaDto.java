package com.robinfood.encuesta.dto;

import java.io.Serializable;

import com.robinfood.encuesta.validator.GuardarRespuestaValidator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@GuardarRespuestaValidator.List({
	@GuardarRespuestaValidator(
		campo = "pregunta",
		name = "pregunta",
		message = "ID de pregunta invalido"
	),
	@GuardarRespuestaValidator(
		campo = "respuestaId",
		name = "respuesta",
		message = "ID de respuesta invalido"
	)		
})
public class GuardarRespuestaDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
    public static final String NAME = "GuardarRespuestaDto";
            
    Long pregunta;
    
    @Default
    String respuesta = "";
    
    @Default
    Long respuestaId = -1L;

}
