package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lmora
 */
@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    @Override
    public ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException {
        return ResponseEntity.ok(this.prestamoService.listarPrestamos());
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> crearPrestamo(PrestamoRq prestamoRq)
            throws BadRequestException {
        return ResponseEntity.ok(this.prestamoService.crearPrestamo(prestamoRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(Prestamo prestamo) throws BadRequestException {
        return ResponseEntity.ok(this.prestamoService.entregarLibro(prestamo));
    }

}
