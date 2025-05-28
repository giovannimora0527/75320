package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 * Entidad que representa la tabla 'categoria'.
 * 
 * Contiene información básica de categorías disponibles en el sistema.
 */
@Entity
@Table(name = "categoria")
@Data
public class Categoria implements Serializable {

    /**
     * Id Serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la categoría.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer categoriaId;

    /**
     * Nombre de la categoría.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}
