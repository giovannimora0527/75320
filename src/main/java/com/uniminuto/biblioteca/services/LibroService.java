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
}
