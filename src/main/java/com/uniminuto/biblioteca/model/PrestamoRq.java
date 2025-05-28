package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class PrestamoRq {

    /**
     * Identificador único del usuario asociado a la operación. Debe ser un ID
     * válido existente en la tabla de usuarios. No puede ser nulo y debe ser un
     * número positivo.
     */
    private Integer usuarioId;

    /**
     * Identificador único del libro involucrado en la operación. Debe
     * corresponder a un ID válido existente en la tabla de libros. No puede ser
     * nulo y debe ser un número positivo.
     */
    private Integer libroId;

    /**
     * Fecha programada o realizada de devolución del libro. Formato esperado:
     * ISO-8601 (yyyy-MM-dd). Para préstamos activos, representa la fecha límite
     * de devolución. Para préstamos finalizados, representa la fecha real de
     * devolución. No puede ser una fecha anterior a la fecha de préstamo.
     */
    private String fechaDevolucion;
}
