package com.robinfood.encuesta.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robinfood.encuesta.dto.EncuestaDto;
import com.robinfood.encuesta.dto.GuardarRespuestaDto;
import com.robinfood.encuesta.dto.PreguntaDto;
import com.robinfood.encuesta.dto.RespuestaPreguntaDto;
import com.robinfood.encuesta.entity.EncuestaEntity;
import com.robinfood.encuesta.entity.PreguntaEntity;
import com.robinfood.encuesta.entity.RespuestaEncuestaEntity;
import com.robinfood.encuesta.entity.RespuestaPreguntaEntity;
import com.robinfood.encuesta.entity.RespuestaSeleccionadaEntity;
import com.robinfood.encuesta.exception.BadRequestException;
import com.robinfood.encuesta.exception.ResourceNotFoundException;
import com.robinfood.encuesta.mapper.EncuestaMapper;
import com.robinfood.encuesta.mapper.PreguntaMapper;
import com.robinfood.encuesta.mapper.RespuestaPreguntaMapper;
import com.robinfood.encuesta.repository.EncuestaRepository;
import com.robinfood.encuesta.repository.PreguntaRepository;
import com.robinfood.encuesta.repository.RespuestaEncuestaRepository;
import com.robinfood.encuesta.repository.RespuestaPreguntaRepository;
import com.robinfood.encuesta.repository.RespuestaSeleccionadaRepository;
import com.robinfood.encuesta.service.EncuestaService;

@Service
public class EncuestaServiceImpl implements EncuestaService {

	@Autowired
	EncuestaRepository encuestaRepository;

	@Autowired
	PreguntaRepository preguntaRepository;

	@Autowired
	RespuestaPreguntaRepository respuestaPreguntaRepository;

	@Autowired
	RespuestaEncuestaRepository respuestaEncuestaRepository;

	@Autowired
	RespuestaSeleccionadaRepository respuestaSeleccionadaRepository;

	@Autowired(required = false)
	EncuestaMapper encuestaMapper = Mappers.getMapper(EncuestaMapper.class);

	@Autowired(required = false)
	PreguntaMapper preguntaMapper = Mappers.getMapper(PreguntaMapper.class);

	@Autowired(required = false)
	RespuestaPreguntaMapper respuestaPreguntaMapper = Mappers.getMapper(RespuestaPreguntaMapper.class);

	@Override
	public EncuestaDto getById(Long id) {
		Optional<EncuestaEntity> optEntity = encuestaRepository.findByIdAndEstado(id, "A");
		if (!optEntity.isPresent())
			throw new ResourceNotFoundException("No existe encuesta con el ID enviado");
		EncuestaDto encuestaDto = encuestaMapper.convertEntityToDto(optEntity.get());
		List<PreguntaEntity> preguntaListEntity = preguntaRepository.findByEncuesta(encuestaDto.getId());
		List<PreguntaDto> preguntaListDto = preguntaMapper.convertEntityListToDtoList(preguntaListEntity);
		for (int i = 0; i < preguntaListDto.size(); i++) {
			List<RespuestaPreguntaEntity> respuestaPreguntaEntities = respuestaPreguntaRepository
					.findByPregunta(preguntaListDto.get(i).getId());
			List<RespuestaPreguntaDto> resuestaPreguntaListDto = respuestaPreguntaMapper
					.convertEntityListToDtoList(respuestaPreguntaEntities);
			preguntaListDto.get(i).setRespuestas(resuestaPreguntaListDto);
		}
		encuestaDto.setPreguntas(preguntaListDto);
		return encuestaDto;
	}

	private List<PreguntaEntity> getPreguntaList(EncuestaEntity encuestaEntity, List<PreguntaDto> preguntaDto) {
		List<PreguntaEntity> preguntaListEntity = preguntaMapper.convertDtoListToEntityList(preguntaDto);
		for (int i = 0; i < preguntaDto.size(); i++) {
			preguntaListEntity.get(i).setEncuesta(encuestaEntity.getId());
			List<RespuestaPreguntaEntity> entities = respuestaPreguntaMapper
					.convertDtoListToEntityList(preguntaDto.get(i).getRespuestas());
			if (entities != null)
				for (int j = 0; j < entities.size(); j++)
					entities.get(j).setPregunta(preguntaListEntity.get(i).getId());
		}
		return preguntaListEntity;
	}

	private List<RespuestaPreguntaEntity> getRespuestaPreguntaList(PreguntaEntity preguntaEntity,
			List<RespuestaPreguntaDto> respuestaPreguntaDto) {
		List<RespuestaPreguntaEntity> respuestaPreguntaListEntity = respuestaPreguntaMapper
				.convertDtoListToEntityList(respuestaPreguntaDto);
		if (respuestaPreguntaDto != null)
			for (int i = 0; i < respuestaPreguntaDto.size(); i++)
				respuestaPreguntaListEntity.get(i).setPregunta(preguntaEntity.getId());

		return respuestaPreguntaListEntity;
	}

	private void savePreguntas(EncuestaEntity encuestaEntity, List<PreguntaDto> preguntaListDto) {
		List<PreguntaEntity> preguntaListEntities = getPreguntaList(encuestaEntity, preguntaListDto);
		for (int i = 0; i < preguntaListEntities.size(); i++) {
			PreguntaEntity preguntaEntity = preguntaListEntities.get(i);
			preguntaEntity.setEncuesta(encuestaEntity.getId());
			preguntaEntity = preguntaRepository.saveAndFlush(preguntaEntity);
			if (preguntaEntity.getId() != null) {
				List<RespuestaPreguntaEntity> respuestaPreguntaListEntities = getRespuestaPreguntaList(preguntaEntity,
						preguntaListDto.get(i).getRespuestas());
				if (respuestaPreguntaListEntities != null)
					respuestaPreguntaRepository.saveAllAndFlush(respuestaPreguntaListEntities);
			}
		}
	}

	@Override
	public Map<String, Object> create(EncuestaDto encuestaDto) {
		EncuestaEntity encuestaEntity = encuestaMapper.convertDtoToEntity(encuestaDto);
		encuestaEntity = encuestaRepository.save(encuestaEntity);

		if (encuestaEntity.getId() != null)
			savePreguntas(encuestaEntity, encuestaDto.getPreguntas());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", (encuestaEntity.getId() != null) ? "Acción realizada correctamente"
				: "Ocurrió un error, intentelo nuevamente");
		map.put("success", (encuestaEntity.getId() != null) ? true : false);
		return map;
	}

	@Override
	public Map<String, Object> delete(Long id) {
		Optional<EncuestaEntity> optEntity = encuestaRepository.findByIdAndEstado(id, "A");
		if (!optEntity.isPresent())
			throw new ResourceNotFoundException("No existe encuesta con el ID enviado");

		EncuestaEntity encuestaEntity = optEntity.get();
		encuestaEntity.setEstado("I");
		encuestaRepository.save(encuestaEntity);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", (encuestaEntity.getId() != null) ? "Acción realizada correctamente"
				: "Ocurrió un error, intentelo nuevamente");
		map.put("success", (encuestaEntity.getId() != null) ? true : false);
		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> responder(List<GuardarRespuestaDto> data) {
		int i = 1;
		for (GuardarRespuestaDto guardarRespuestaDto : data) {
			PreguntaEntity preguntaEntity = preguntaRepository.findById(guardarRespuestaDto.getPregunta()).get();
			String tipoPregunta = preguntaEntity.getTipo().getNombre();
			RespuestaEncuestaEntity respuestaEncuestaEntity = new RespuestaEncuestaEntity();

			if (tipoPregunta.equalsIgnoreCase("ABIERTA") && guardarRespuestaDto.getRespuesta().equalsIgnoreCase(""))
				throw new BadRequestException("La respuesta N." + i + " es tipo abierta y no es valida");
			if (tipoPregunta.equalsIgnoreCase("UNICA") && guardarRespuestaDto.getRespuestaId() == -1)
				throw new BadRequestException("El ID de la respuesta N." + i + " no es valido");

			respuestaEncuestaEntity.setPregunta(guardarRespuestaDto.getPregunta());
			respuestaEncuestaEntity.setRespuesta(guardarRespuestaDto.getRespuesta());
			respuestaEncuestaEntity = respuestaEncuestaRepository.saveAndFlush(respuestaEncuestaEntity);

			if (guardarRespuestaDto.getRespuestaId() > 0) {
				RespuestaSeleccionadaEntity respuestaSeleccionadaEntity = new RespuestaSeleccionadaEntity();
				respuestaSeleccionadaEntity.setRespuestaEncuesta(respuestaEncuestaEntity.getId());
				respuestaSeleccionadaEntity.setRespuestaPregunta(guardarRespuestaDto.getRespuestaId());
				respuestaSeleccionadaRepository.saveAndFlush(respuestaSeleccionadaEntity);
			}
			i++;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Acción realizada correctamente");
		map.put("success", true);
		return map;
	}

}
