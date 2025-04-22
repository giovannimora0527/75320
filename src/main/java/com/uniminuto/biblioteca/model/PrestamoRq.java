package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class PrestamoRq {
    private Integer usuarioId;
    private Integer libroId;
    private String fechaDevolucion;
}
