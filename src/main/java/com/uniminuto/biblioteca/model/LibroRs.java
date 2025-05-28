
package com.uniminuto.biblioteca.model;

import lombok.Data;

@Data
public class LibroRs {
    private String titulo;
    private String categoria;
    private int existencias;
    private String nombreAutor;
    private int anioPublicacion;
}
