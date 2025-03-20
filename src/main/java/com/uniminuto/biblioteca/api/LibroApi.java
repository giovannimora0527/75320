package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/libro")
public interface LibroApi {

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
    ResponseEntity<List<Libro>> listarLibros()
            throws BadRequestException;

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param libroId Id del libro.
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> obtenerLibroPorId(@RequestParam Integer libroId)
            throws BadRequestException;

    @RequestMapping(value = "/obtenerLibroAutor",
            produces = {"application/json"},
            //            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> listarLibroAutorId(@RequestParam Integer autorId)
            throws BadRequestException;

    @RequestMapping(value = "/obtenerTituloLibro",
            produces = {"application/json"},
            //            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> buscarNombreLibro(@RequestParam String titulo)
            throws BadRequestException;

    @RequestMapping(value = "/listarPorFechas",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> listarLibrosRangoFechas(@RequestParam Integer fechaInicio, @RequestParam Integer fechaFin)
            throws BadRequestException;
}
