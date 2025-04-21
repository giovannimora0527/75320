package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author lmora
 */
@Data
public class AutorRq {
    private String nombre;
    private String nacionalidad;

    @JsonFormat(pattern = "yyyy-MM-dd") 
    private LocalDate fechaNacimiento;
}