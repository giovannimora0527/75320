package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface AutorService {
    List<Autor> obtenerListadoAutores();
    
    List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad) throws BadRequestException;
    
    Autor obtenerAutorPorId(Integer autorId) throws BadRequestException;

    boolean existeAutorPorNombre(String nombre);
    Autor crearAutor(Autor autor);
    Autor actualizarAutor(Integer id, Autor autor);

}
