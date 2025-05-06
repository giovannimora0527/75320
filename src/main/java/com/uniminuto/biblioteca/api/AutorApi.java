package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Sofía Pedraza
 */
@CrossOrigin(origins = "*")
@RequestMapping("/autor")
public interface AutorApi {

    /**
     * Método para listar los autores registrados en la base de datos.
     *
     * @return Lista de autores.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(
            value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    ResponseEntity<List<Autor>> listarAutores() throws BadRequestException;

    /**
     * Método para buscar un autor por nombre.
     *
     * @param nombre nombre del autor.
     * @return Autor encontrado.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(value = "/buscar-por-nombre",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Autor> buscarAutorPorNombre(
            @RequestParam String nombre)
            throws BadRequestException;

    /**
     * Guarda un nuevo autor.
     *
     * @param autor autor a guardar.
     * @return respuesta del servicio.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(value = "/guardar-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<AutorRs> guardarAutor(@RequestBody AutorRq autor)
            throws BadRequestException;

    /**
     * Actualiza un autor existente.
     *
     * @param autor autor a actualizar.
     * @return respuesta del servicio.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(value = "/actualizar-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<AutorRs> actualizarAutor(@RequestBody Autor autor)
            throws BadRequestException;

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param nacionalidad nacionalidad del autor.
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-nacionalidad",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Autor>> listarAutoresByNacionalidad(
            @RequestParam String nacionalidad)
            throws BadRequestException;

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-autor-id",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Autor> listarAutorPorId(@RequestParam Integer autorId)
            throws BadRequestException;

}