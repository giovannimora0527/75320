package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {
    
    /**
     * Metodo para listar los préstamos registrados en bd.
     *
     * @return Lista de préstamos.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Prestamo>> listarPrestamos()
            throws BadRequestException;
    
    /**
     * Metodo para obtener un préstamo por id.
     *
     * @param prestamoId id del préstamo.
     * @return El préstamo.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-prestamo-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Prestamo> listarPrestamoPorId(@RequestParam Integer prestamoId)
            throws BadRequestException;
    
    /**
     * Metodo para guardar un préstamo. Si el préstamo tiene id nulo se crea, 
     * si tiene id existente se actualiza.
     *
     * @param prestamo datos del préstamo.
     * @return Respuesta generica con el resultado.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/guardar-prestamo",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<RespuestaGenerica> guardarPrestamo(
        @RequestBody PrestamoRq prestamo)
        throws BadRequestException;
    
    /**
 * Método para crear un nuevo préstamo.
 *
 * @param prestamo entidad completa del préstamo.
 * @return El préstamo creado.
 * @throws BadRequestException excepcion.
 */
    @RequestMapping(value = "/crear-prestamo",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<Prestamo> crearPrestamo(@RequestBody Prestamo prestamo)
        throws BadRequestException;
}
