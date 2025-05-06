/**
 * 
 * @author Sofía Pedraza
 */
package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;

/**
 * Clase que representa la solicitud de préstamo de un libro.
 */
@Data
public class PrestamoRq {

    /**
     * Identificador del usuario que realiza el préstamo.
     */
    private Integer idUsuario;

    /**
     * Identificador del libro a prestar.
     */
    private Integer idLibro;


    /**
     * Fecha esperada para la devolución del libro.
     */
    private LocalDate fechaDevolucion;
}