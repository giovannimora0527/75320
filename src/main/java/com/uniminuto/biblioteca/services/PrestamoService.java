package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import java.util.List;

public interface PrestamoService {
    List<Prestamo> listarPrestamos();
    Prestamo guardarPrestamo(Prestamo prestamo);
    Prestamo actualizarPrestamo(Prestamo prestamo);
    Prestamo buscarPrestamoPorId(Long id);
}
