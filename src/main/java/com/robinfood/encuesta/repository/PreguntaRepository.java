package com.robinfood.encuesta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robinfood.encuesta.entity.EncuestaEntity;
import com.robinfood.encuesta.entity.PreguntaEntity;

public interface PreguntaRepository extends JpaRepository<PreguntaEntity, Long>{

	public List<PreguntaEntity> findByEncuesta(Long id);
	
}
