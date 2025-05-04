package com.uniminuto.biblioteca.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PrestamoRq {
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDate fechaEntrega;
    private String estado;
}
