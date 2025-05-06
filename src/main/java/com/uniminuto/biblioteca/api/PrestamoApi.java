/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Sofía Pedraza
 */
/**
 * API para operaciones sobre los préstamos en la biblioteca.
 */
@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {

        /**
         * Método para listar los préstamos registrados en la base de datos.
         *
         * @return Lista de préstamos.
         * @throws org.apache.coyote.BadRequestException
         */
        @RequestMapping(value = "/listar-prestamo", method = RequestMethod.GET, produces = { "application/json" })
        ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException;

        /**
         * Guarda un nuevo préstamo.
         * 
         * @param prestamoRq detalles del préstamo.
         * @return respuesta del servicio.
         * @throws BadRequestException excepción.
         */
        @RequestMapping(value = "/prestar", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.POST)
        ResponseEntity<PrestamoRs> guardarPrestamo(@RequestBody PrestamoRq prestamoRq)
                        throws BadRequestException;

        /**
         * Actualiza un préstamo existente.
         * 
         * @param prestamo detalles del préstamo a actualizar.
         * @return respuesta del servicio.
         * @throws BadRequestException excepción.
         */
        @RequestMapping(value = "/devolucion-prestamo", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.POST)
        ResponseEntity<PrestamoRs> actualizarPrestamo(@RequestBody Prestamo prestamo)
                        throws BadRequestException;


}