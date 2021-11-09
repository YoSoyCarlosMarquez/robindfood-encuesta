package com.robinfood.encuesta.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.robinfood.encuesta.dto.EncuestaDto;
import com.robinfood.encuesta.entity.EncuestaEntity;

@Mapper
public interface EncuestaMapper {
	
	EncuestaDto convertEntityToDto(EncuestaEntity encuestaEntity);

	EncuestaEntity convertDtoToEntity(EncuestaDto encuestaDto);

    List<EncuestaDto> convertEntityListToDtoList(List<EncuestaEntity> encuestaEntities);

}
