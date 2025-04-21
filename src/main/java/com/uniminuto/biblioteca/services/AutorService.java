package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface AutorService {
    
    List<Autor> obtenerListadoAutores();
    
    Autor crearAutor(Autor autor);
    
    List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad) throws BadRequestException;
    
    Autor obtenerAutorPorId(Integer autorId) throws BadRequestException;
    
    RespuestaGenerica guardarAutor(AutorRq Autor) throws BadRequestException;
    
    RespuestaGenerica actualizarAutor(Autor Autor) throws BadRequestException;
}
