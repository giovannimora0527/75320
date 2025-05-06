/**
 * @author Sofía Pedraza
*/
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador API que implementa las operaciones relacionadas con los préstamos.
 */
@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    /**
     * Obtiene la lista de todos los préstamos registrados en el sistema.
     * 
     * @return ResponseEntity con la lista de préstamos
     * @throws BadRequestException si ocurre un error durante la consulta
     */
    @Override
    public ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    /**
     * Guarda un nuevo préstamo en el sistema.
     * 
     * @param prestamoRq datos del nuevo préstamo
     * @return ResponseEntity con el mensaje de éxito o error de la operación
     * @throws BadRequestException si el préstamo no puede ser guardado debido a un error
     */
    @Override
    public ResponseEntity<PrestamoRs> guardarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        PrestamoRs response = prestamoService.guardarPrestamoNuevo(prestamoRq);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza un préstamo existente en el sistema.
     * 
     * @param prestamo datos del préstamo a actualizar
     * @return ResponseEntity con el mensaje de éxito o error de la operación
     * @throws BadRequestException si el préstamo no puede ser actualizado debido a un error
     */
    @Override
    public ResponseEntity<PrestamoRs> actualizarPrestamo(Prestamo prestamo) throws BadRequestException {
        PrestamoRs response = prestamoService.actualizarPrestamo(prestamo);
        return ResponseEntity.ok(response);
    }

}