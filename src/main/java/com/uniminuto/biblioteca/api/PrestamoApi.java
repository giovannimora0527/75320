package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.model.PrestamoRq;
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
 * @author [TuNombre]
 */
@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {

    /**
     * Método para listar todos los préstamos registrados en BD.
     *
     * @return Lista de préstamos.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException;

    /**
     * Método para buscar un préstamo por su id.
     *
     * @param id identificador del préstamo.
     * @return Préstamo encontrado.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(value = "/buscar-por-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Prestamo> buscarPrestamoPorId(@RequestParam Long id) throws BadRequestException;

    /**
     * Método para guardar un nuevo préstamo.
     *
     * @param prestamo datos del préstamo a guardar.
     * @return respuesta genérica.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(value = "/guardar-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenerica> guardarPrestamo(@RequestBody PrestamoRq prestamo) throws BadRequestException;

    /**
     * Método para actualizar un préstamo existente.
     *
     * @param prestamo datos del préstamo actualizado.
     * @return respuesta genérica.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(value = "/actualizar-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenerica> actualizarPrestamo(@RequestBody Prestamo prestamo) throws BadRequestException;

}
