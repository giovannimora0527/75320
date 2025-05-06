package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import java.time.LocalDate;
import java.util.List;

public interface PrestamoService {
    List<Prestamo> listarPrestamos();
    Prestamo crearPrestamo(Prestamo prestamo);
    Prestamo actualizarEntrega(Long idPrestamo, LocalDate fechaEntrega);
}
