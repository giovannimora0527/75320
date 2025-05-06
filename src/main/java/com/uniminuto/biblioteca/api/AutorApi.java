package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Autor;
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
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/autor")
public interface AutorApi {

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            //            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Autor>> listarAutores()
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
            //            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Autor>> listarAutoresByNacionalidad(
            @RequestParam String nacionalidad)
            throws BadRequestException;

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param autorIds
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-autor-id",
            produces = {"application/json"},
            //            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Autor> listarAutorPorId(@RequestParam Integer autorIds)
            throws BadRequestException;

    @RequestMapping(value = "/crear",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    ResponseEntity<Autor> crearAutor(@RequestBody Autor autor) throws BadRequestException;

    @RequestMapping(value = "/actualizar",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"})
    ResponseEntity<Autor> actualizarAutor(
            @RequestBody Autor autor) throws BadRequestException;

    @RequestMapping(value = "/eliminar",
            method = RequestMethod.DELETE,
            produces = {"application/json"})
    ResponseEntity<String> eliminarAutor(@RequestParam Integer autorId) throws BadRequestException;
}
