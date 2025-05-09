package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import java.time.LocalDate;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface PrestamoService {

    List<Prestamo> listarPrestamos() throws BadRequestException;

    Prestamo obtenerPrestamoPorId(Integer idPrestamo) throws BadRequestException;

    List<Prestamo> obtenerPrestamosPorUsuario(String usuario) throws BadRequestException;

    List<Prestamo> obtenerPrestamosPorEstado(String estado) throws BadRequestException;

    List<Prestamo> obtenerPrestamosPorLibro(Integer idLibro) throws BadRequestException;

    List<Prestamo> obtenerPrestamosPorRangoFecha(LocalDate inicio, LocalDate fin) throws BadRequestException;

    public RespuestaGenerica crearPrestamo(PrestamoRq prestamo);
}
