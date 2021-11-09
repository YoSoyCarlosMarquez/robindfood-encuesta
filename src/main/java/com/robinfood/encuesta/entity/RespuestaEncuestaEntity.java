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
@Table(name = "respuesta_encuesta")
@Entity
public class RespuestaEncuestaEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESPENCID", nullable = false)
    private Long id;
	
	@Column(name = "RESPENCPREGUNTA", nullable = false)
    private Long pregunta;
	
	@Column(name = "RESPENCRESPUESTA", nullable = true)
    private String respuesta;
	
}
