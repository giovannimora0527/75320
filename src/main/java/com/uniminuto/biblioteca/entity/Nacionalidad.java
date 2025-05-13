package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
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
 * Entidad que representa una nacionalidad.
 * 
 * Mapea la tabla 'nacionalidad' de la base de datos.
 */
@Entity
@Table(name = "nacionalidad")
@Data
public class Nacionalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la nacionalidad.
     * Es la clave primaria de la tabla.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nacionalidad_id")
    private Integer nacionalidadId;

    /**
     * Nombre de la nacionalidad.
     * No puede ser nulo y tiene una longitud máxima de 100 caracteres.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}