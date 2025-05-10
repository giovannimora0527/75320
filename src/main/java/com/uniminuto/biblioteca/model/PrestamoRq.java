package com.uniminuto.biblioteca.model;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PrestamoRq {
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucion;
    private LocalDateTime fechaEntrega;
    private String estado;
    
     /**
     * Identificador del usuario que realiza el préstamo.
     */
    private Integer idUsuario;

    /**
     * Identificador del libro a prestar.
     */
    private Integer idLibro;


}
