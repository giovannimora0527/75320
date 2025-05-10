package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador que implementa la API de préstamos.
 * Permite listar, buscar, guardar y actualizar préstamos de la biblioteca.
 * 
 * Autor: [TuNombre]
 */
@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException {
        try {
            List<Prestamo> prestamos = prestamoService.listarPrestamos();
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            throw new BadRequestException("Error al listar préstamos: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Prestamo> buscarPrestamoPorId(Long id) throws BadRequestException {
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
            if (prestamo == null) {
                throw new BadRequestException("No se encontró el préstamo con ID: " + id);
            }
            return ResponseEntity.ok(prestamo);
        } catch (Exception e) {
            throw new BadRequestException("Error al buscar préstamo: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<PrestamoRs> guardarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        PrestamoRs response = prestamoService.guardarPrestamoNuevo(prestamoRq);
        return ResponseEntity.ok(response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<RespuestaGenerica> actualizarPrestamo(Prestamo prestamo) throws BadRequestException {
        try {
            prestamoService.actualizarPrestamo(prestamo);

            RespuestaGenerica respuesta = new RespuestaGenerica();
            respuesta.setMensaje("Préstamo actualizado exitosamente.");
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar préstamo: " + e.getMessage());
        }
    }
}
