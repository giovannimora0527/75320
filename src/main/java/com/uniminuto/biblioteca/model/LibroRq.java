package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 * DTO para la creación y actualización de un libro.
 */
@Data
public class LibroRq {

    private String titulo;
    private Integer autorId;  
    private Integer anioPublicacion;
    private String categoria;
    private Integer existencias;

    
}
