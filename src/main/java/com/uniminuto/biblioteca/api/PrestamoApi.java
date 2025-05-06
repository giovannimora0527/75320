
package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.model.LibroDisponibleDto;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.model.PrestamoEntregaRq;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.model.UsuarioDisponibleDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Andres Peña
 */

@CrossOrigin(origins = "*")
@RequestMapping("/prestamos")
public interface PrestamoApi {
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<PrestamoRs>> listarPrestamos()
            throws BadRequestException;

    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenerica> guardarPrestamo(
            @RequestBody PrestamoRq rq)
            throws BadRequestException;

    @RequestMapping(value = "/actualizar-entrega",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenerica> actualizarEntrega(
            @RequestBody PrestamoEntregaRq rq)
            throws BadRequestException;
    
    @RequestMapping(value = "/usuarios-disponibles", 
            method = RequestMethod.GET)
    ResponseEntity<List<UsuarioDisponibleDto>> obtenerUsuariosDisponibles() throws BadRequestException;

    @RequestMapping(value = "/libros-disponibles", 
            method = RequestMethod.GET)
    ResponseEntity<List<LibroDisponibleDto>> obtenerLibrosDisponibles() throws BadRequestException;
}
