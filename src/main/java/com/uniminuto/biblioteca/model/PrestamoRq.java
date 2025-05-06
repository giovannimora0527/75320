package com.uniminuto.biblioteca.model;
import lombok.Data;
import java.time.LocalDate;
/**
 *
 * @author Andres Peña
 */
@Data
public class PrestamoRq {
    private Integer usuarioId;           // ID del usuario que se selecciona desde Angular
    private Integer libroId;             // ID del libro que se selecciona desde Angular
    private LocalDate fechaDevolucion;   // Fecha en la que se debe devolver el libro
}
