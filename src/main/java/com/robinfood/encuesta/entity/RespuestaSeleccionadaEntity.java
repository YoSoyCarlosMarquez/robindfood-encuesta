package com.robinfood.encuesta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "respuesta_seleccionada")
@Entity
public class RespuestaSeleccionadaEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESPSELID", nullable = false)
    private Long id;
	
	@Column(name = "RESPSELRESPENCID", nullable = false)
    private Long respuestaEncuesta;
	
	@Column(name = "RESPSELRESPREID", nullable = false)
    private Long respuestaPregunta;

}
