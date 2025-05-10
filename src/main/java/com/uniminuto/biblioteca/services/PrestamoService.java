package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface PrestamoService {
    List<Prestamo> listarPrestamos();
    PrestamoRs guardarPrestamoNuevo(PrestamoRq prestamoRq) throws BadRequestException;
    Prestamo actualizarPrestamo(Prestamo prestamo);
    Prestamo buscarPrestamoPorId(Long id);
}
