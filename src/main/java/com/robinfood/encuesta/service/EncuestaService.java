package com.robinfood.encuesta.service;

import java.util.List;
import java.util.Map;

import com.robinfood.encuesta.dto.EncuestaDto;
import com.robinfood.encuesta.dto.GuardarRespuestaDto;

public interface EncuestaService {
	
	public EncuestaDto getById(Long id);
	
	public Map<String, Object> create(EncuestaDto data);
	
	public Map<String, Object> delete(Long id);
	
	public Map<String, Object> responder(List<GuardarRespuestaDto> data);

}
