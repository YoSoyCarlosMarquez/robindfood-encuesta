package com.robinfood.encuesta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "respuesta_pregunta")
@Entity
public class RespuestaPreguntaEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESPREID", nullable = false)
    private Long id;
	
    //@Column(name = "RESPREPREGUNTA", nullable = false)
    /*@ManyToOne
    @JoinColumn(name = "RESPREPREGUNTA")
    private PreguntaEntity pregunta;*/
	
	@Column(name = "RESPREPREGUNTA", nullable = true)
    private Long pregunta;
	
    @Column(name = "RESPRETEXTO", nullable = true)
    private String descripcion;
    
    @Column(name = "RESPREESTADO", nullable = false, length = 1, columnDefinition = "CHAR DEFAULT 'A'")
    private String estado;

}
