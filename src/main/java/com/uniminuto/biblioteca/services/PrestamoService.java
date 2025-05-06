
package com.uniminuto.biblioteca.services;
import com.uniminuto.biblioteca.model.LibroDisponibleDto;
import com.uniminuto.biblioteca.model.PrestamoEntregaRq;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.model.UsuarioDisponibleDto;
import org.apache.coyote.BadRequestException;

import java.util.List;
/**
 *
 * @author Andres Peña
 */
public interface PrestamoService {
    /**
     * Lista todos los préstamos.
     * @return 
     */
    List<PrestamoRs> listarTodo();

    List<UsuarioDisponibleDto> obtenerUsuariosDisponiblesParaPrestamo() throws BadRequestException;

    List<LibroDisponibleDto> obtenerLibrosDisponiblesParaPrestamo() throws BadRequestException;
    /**
     * Guarda un nuevo préstamo.
     *
     * @param rq datos del préstamo
     * @return respuesta con mensaje
     * @throws BadRequestException si hay errores de validación
     */
    RespuestaGenerica guardarPrestamo(PrestamoRq rq) throws BadRequestException;

    /**
     * Actualiza la fecha de entrega de un préstamo.
     *
     * @param rq datos de entrega
     * @return respuesta con mensaje
     * @throws BadRequestException si hay validaciones fallidas
     */
    RespuestaGenerica actualizarEntrega(PrestamoEntregaRq rq) throws BadRequestException;
}
