package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.RespuestaGenerica;

import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author Angie Vanegas Nieto
 */
public interface PrestamoService {
    List<Prestamo> obtenerListadoDePrestamos();
    Optional<Prestamo> obtenerPorId(Integer id)throws BadRequestException;
    RespuestaGenerica guardarPrestamo(Prestamo prestamo)throws BadRequestException;
    Prestamo crearPrestamo(Prestamo prestamo) throws BadRequestException;
    
}
