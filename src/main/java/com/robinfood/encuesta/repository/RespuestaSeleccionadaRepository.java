package com.robinfood.encuesta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robinfood.encuesta.entity.RespuestaSeleccionadaEntity;

@Repository
public interface RespuestaSeleccionadaRepository extends JpaRepository<RespuestaSeleccionadaEntity, Long>{

}
