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
@Table(name = "tipo_pregunta")
@Entity
public class TipoPreguntaEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIPPREID", nullable = false)
    private Long id;
    @Column(name = "TIPPRENOMBRE", nullable = false)
    private String nombre;
    @Column(name = "TIPPREESTADO", length = 1, nullable = false)
    private String estado;

}

