package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {
    
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException;
    
    @RequestMapping(value = "/crear",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> crearPrestamo(@RequestBody PrestamoRq prestamoRq) 
            throws BadRequestException;
    
    @RequestMapping(value = "/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(@RequestBody PrestamoRq prestamoRq) 
            throws BadRequestException;
}
