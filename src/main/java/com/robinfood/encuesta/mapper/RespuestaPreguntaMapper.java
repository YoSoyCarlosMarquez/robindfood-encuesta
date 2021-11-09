package com.robinfood.encuesta.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.robinfood.encuesta.dto.RespuestaPreguntaDto;
import com.robinfood.encuesta.entity.RespuestaPreguntaEntity;

@Mapper
public interface RespuestaPreguntaMapper {


	RespuestaPreguntaDto convertEntityToDto(RespuestaPreguntaEntity entity);

	RespuestaPreguntaEntity convertDtoToEntity(RespuestaPreguntaDto dto);

    List<RespuestaPreguntaDto> convertEntityListToDtoList(List<RespuestaPreguntaEntity> entities);
    
    List<RespuestaPreguntaEntity> convertDtoListToEntityList(List<RespuestaPreguntaDto> entities);
		
}
