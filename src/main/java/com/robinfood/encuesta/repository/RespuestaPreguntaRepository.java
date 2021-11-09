package com.robinfood.encuesta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robinfood.encuesta.entity.RespuestaPreguntaEntity;

public interface RespuestaPreguntaRepository extends JpaRepository<RespuestaPreguntaEntity, Long>{
	
	public List<RespuestaPreguntaEntity> findByPregunta(Long id);

}
