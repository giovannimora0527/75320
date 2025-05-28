package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.model.CargaMasivaError;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lmora
 */
public interface AutorService {
    
    /**
     * Lista todos los autores registrados.
     * 
     * @return lista de autores.
     * @throws BadRequestException si ocurre un error.
     */
    List<AutorRs> listarAutores() throws BadRequestException;

    /**
     * Guarda un nuevo autor en la base de datos.
     * 
     * @param autor datos del nuevo autor.
     * @return respuesta genérica de éxito o error.
     * @throws BadRequestException si ya existe un autor con el mismo nombre.
     */
    RespuestaGenerica guardarAutor(AutorRq autor) throws BadRequestException;

    /**
     * Actualiza un autor existente.
     * 
     * @param autor datos actualizados del autor.
     * @return respuesta genérica de éxito o error.
     * @throws BadRequestException si no existe el autor original o el nuevo nombre ya está en uso.
     */
    RespuestaGenerica actualizarAutor(AutorRq autor) throws BadRequestException;
    
    
    
    
    
    List<Autor> obtenerListadoAutores();
    
    List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad) throws BadRequestException;
    
    /*
     * Servicio para subir el archivo .csv con los registros de autores
    */
    List<CargaMasivaError> cargarAutoresDesdeCsv(MultipartFile file);
    
    Autor obtenerAutorPorId(Integer autorId) throws BadRequestException;
}
