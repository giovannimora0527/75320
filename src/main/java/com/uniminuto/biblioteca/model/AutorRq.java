
package com.uniminuto.biblioteca.model;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author Angie Vanegas Nieto
 */

@Data
public class AutorRq {
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
}
