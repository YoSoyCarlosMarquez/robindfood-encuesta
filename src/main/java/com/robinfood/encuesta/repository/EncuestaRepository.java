package com.robinfood.encuesta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robinfood.encuesta.entity.EncuestaEntity;

@Repository
public interface EncuestaRepository extends JpaRepository<EncuestaEntity, Long>{
	
	public Optional<EncuestaEntity> findByIdAndEstado(Long id, String estado);
		
}
