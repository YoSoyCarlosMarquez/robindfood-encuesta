package com.robinfood.encuesta.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
@Table(name = "encuesta")
@Entity
public class EncuestaEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENCID", nullable = false)
    private Long id;
	
    @Column(name = "ENCTITULO", nullable = false)
    private String titulo;
    
    @Column(name = "ENCDESCRIPCION", nullable = true)
    private String descripcion;
    
    @Column(name = "ENCFECHA", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate fecha;
    
    @Column(name = "ENCESTADO", nullable = false, length = 1, columnDefinition = "CHAR DEFAULT 'A'")
    private String estado;
    
    /*@OneToMany(targetEntity = PreguntaEntity.class)
    @JoinColumn(name = "PREENCUESTA", referencedColumnName = "ENCID")
    @Cascade(value = CascadeType.ALL)
    private List<PreguntaEntity> preguntas;*/
    
}
