package com.robinfood.encuesta.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pregunta")
@Entity
public class PreguntaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PREID", nullable = false)
	private Long id;
	
	//@ManyToOne
    @Column(name = "PREENCUESTA")
	private Long encuesta;
	
	@Column(name = "PREPREGUNTA", nullable = false)
	private String pregunta;
	
	@ManyToOne
	@JoinColumn(name = "PRETIPO", referencedColumnName = "TIPPREID", nullable = false)
	private TipoPreguntaEntity tipo;

	@Column(name = "PREESTADO", nullable = false, length = 1)
	private String estado;
	
	/*@OneToMany(targetEntity = RespuestaPreguntaEntity.class)
    @JoinColumn(name = "RESPREPREGUNTA", referencedColumnName = "PREID")
    @Cascade(value = CascadeType.ALL)
    private List<RespuestaPreguntaEntity> respuestas;*/

}
