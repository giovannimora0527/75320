package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz del servicio.
 * @author lmora
 */
public interface AutorService {

    /**
     * Obtiene un listado de todos los autores registrados en el sistema.
     *
     * @return una lista con todos los autores disponibles, o una lista vacía si
     * no hay autores registrados
     */
    List<Autor> obtenerListadoAutores();

    /**
     * Obtiene un listado de autores filtrados por nacionalidad.
     *
     * @param nacionalidad la nacionalidad por la cual filtrar los autores
     * @return una lista de autores que coinciden con la nacionalidad
     * especificada, o una lista vacía si no se encuentran coincidencias
     * @throws BadRequestException si el parámetro nacionalidad es nulo o vacío
     */
    List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad) 
            throws BadRequestException;

    /**
     * Obtiene un autor específico buscado por su identificador único.
     *
     * @param autorId el identificador único del autor a buscar
     * @return el objeto Autor correspondiente al ID proporcionado
     * @throws BadRequestException si el ID proporcionado es nulo, no existe o
     * no es válido
     */
    Autor obtenerAutorPorId(Integer autorId) throws BadRequestException;

    /**
     * Crea un nuevo registro de autor en el sistema.
     *
     * @param autorRq objeto de solicitud con los datos del autor a crear
     * @return objeto de respuesta genérica que indica el resultado de la
     * operación
     * @throws BadRequestException si los datos proporcionados son inválidos,
     * incompletos o si ya existe un autor con el mismo nombre
     */
    RespuestaGenericaRs crearAutor(AutorRq autorRq) throws BadRequestException;

    /**
     * Actualiza la información de un autor existente en el sistema.
     *
     * @param autor objeto Autor con los datos actualizados
     * @return objeto de respuesta genérica que indica el resultado de la
     * operación
     * @throws BadRequestException si el autor no existe, si los datos
     * proporcionados son inválidos o si ocurre un error durante la
     * actualización
     */
    RespuestaGenericaRs actualizarAutor(Autor autor) throws BadRequestException;
}
