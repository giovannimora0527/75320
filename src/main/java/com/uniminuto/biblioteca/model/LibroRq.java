package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class LibroRq {
    /**
     * titulo.
     */
    private String titulo;
    /**
     * categoriaId.
     */
    private Integer categoriaId;
    /**
     * anioPublicacion.
     */
    private Integer anioPublicacion;
    /**
     * existencias.
     */
    private Integer existencias;
    /**
     * autorId.
     */
    private Integer autorId;
   
}
