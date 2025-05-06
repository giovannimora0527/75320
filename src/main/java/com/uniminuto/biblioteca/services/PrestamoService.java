/**
 * 
 * @author Sofía Pedraza
 */
package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import org.apache.coyote.BadRequestException;
import java.util.List;

/**
 * Interfaz que define las operaciones para gestionar los préstamos.
 */
public interface PrestamoService {

    /**
     * Lista todos los préstamos registrados en el sistema.
     * 
     * @return lista de préstamos
     * @throws BadRequestException si ocurre un error durante la consulta
     */
    List<Prestamo> listarTodos() throws BadRequestException;

    /**
     * Guarda un nuevo préstamo en el sistema.
     * 
     * @param prestamoRq datos del préstamo a guardar
     * @return respuesta con el mensaje de éxito o error de la operación
     * @throws BadRequestException si ocurre un error al guardar el préstamo
     */
    PrestamoRs guardarPrestamoNuevo(PrestamoRq prestamoRq) throws BadRequestException;

    /**
     * Actualiza un préstamo existente en el sistema.
     * 
     * @param prestamo datos del préstamo a actualizar
     * @return respuesta con el mensaje de éxito o error de la operación
     * @throws BadRequestException si ocurre un error al actualizar el préstamo
     */
    PrestamoRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException;

}