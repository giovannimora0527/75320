package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.model.AutorRq;
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

    RespuestaGenerica guardarAutor(AutorRq autor) throws BadRequestException;

    RespuestaGenerica actualizarAutor(Autor autor) throws BadRequestException;
}