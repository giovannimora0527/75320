package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
@Entity
@Table(name = "autores")
public class Autor implements Serializable {
    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador único del autor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Integer autorId;
    
    /**
     * Nombre del autor.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    /**
     * Nacionalidad del autor.
     */    
    @ManyToOne
    @JoinColumn(name = "nacionalidad_id", nullable = false)
    private Nacionalidad nacionalidad;
    
    /**
     * Fecha de nacimiento del autor.
     */
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
}
