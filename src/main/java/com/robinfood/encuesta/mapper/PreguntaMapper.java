package com.robinfood.encuesta.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.robinfood.encuesta.dto.PreguntaDto;
import com.robinfood.encuesta.entity.PreguntaEntity;

@Mapper
public interface PreguntaMapper {
	
	PreguntaDto convertEntityToDto(PreguntaEntity entity);

	PreguntaEntity convertDtoToEntity(PreguntaDto dto);

    List<PreguntaDto> convertEntityListToDtoList(List<PreguntaEntity> entities);
    
    List<PreguntaEntity> convertDtoListToEntityList(List<PreguntaDto> entities);

}
