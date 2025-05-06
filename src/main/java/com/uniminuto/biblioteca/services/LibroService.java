package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface LibroService {

    List<Libro> listarLibros() throws BadRequestException;

    Libro obtenerLibroId(Integer libroId) throws BadRequestException;

    List<Libro> listarLibroAutorId(Integer idAutor) throws BadRequestException;

    Libro buscarNombreLibro(String titulo) throws BadRequestException;

    List<Libro> listarLibrosRangoFechas(Integer fechaInicio, Integer fechaFin) throws BadRequestException;

}
