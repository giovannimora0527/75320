package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author Sofía Pedraza
 */
public interface AutorService {
 List<Autor> listarTodo() throws BadRequestException;
    Autor buscarPorNombre(String nombre) throws BadRequestException;
    AutorRs guardarAutorNuevo(AutorRq autor) throws BadRequestException;
    AutorRs actualizarAutor(Autor autor) throws BadRequestException;
    
    List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad) throws BadRequestException;
    
    Autor obtenerAutorPorId(Integer autorId) throws BadRequestException;
}