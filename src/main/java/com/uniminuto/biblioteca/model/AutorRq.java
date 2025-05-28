package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class AutorRq {

    /**
     * Fecha de nacimiento del autor. Formato esperado: ISO-8601 (yyyy-MM-dd).
     * No puede ser una fecha futura.
     */
    private LocalDate fechaNacimiento;

    /**
     * Identificador único de la nacionalidad del autor. Debe corresponder a un
     * ID válido existente en la tabla de nacionalidades. No puede ser nulo.
     */
    private Integer nacionalidadId;

    /**
     * Nombre completo del autor. Debe contener al menos 2 palabras (nombre y
     * apellido). Longitud máxima: 100 caracteres. No puede ser nulo o vacío.
     */
    private String nombre;
}
