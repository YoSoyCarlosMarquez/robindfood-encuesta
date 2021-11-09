package com.robinfood.encuesta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robinfood.encuesta.entity.TipoPreguntaEntity;

@Repository
public interface TipoPreguntaRepository extends JpaRepository<TipoPreguntaEntity, Long> {

}
