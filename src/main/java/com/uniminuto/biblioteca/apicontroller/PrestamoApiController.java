
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.model.LibroDisponibleDto;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.model.PrestamoEntregaRq;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.model.UsuarioDisponibleDto;
import com.uniminuto.biblioteca.services.PrestamoService;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Andres Peña
 */

@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    @Override
    public ResponseEntity<List<PrestamoRs>> listarPrestamos() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.listarTodo());
    }

    @Override
    public ResponseEntity<RespuestaGenerica> guardarPrestamo(PrestamoRq rq) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.guardarPrestamo(rq));
    }

    @Override
    public ResponseEntity<RespuestaGenerica> actualizarEntrega(PrestamoEntregaRq rq) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.actualizarEntrega(rq));
    }
    
    @Override
    public ResponseEntity<List<UsuarioDisponibleDto>> obtenerUsuariosDisponibles() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerUsuariosDisponiblesParaPrestamo());
    }

    @Override
    public ResponseEntity<List<LibroDisponibleDto>> obtenerLibrosDisponibles() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerLibrosDisponiblesParaPrestamo());
    }
}
