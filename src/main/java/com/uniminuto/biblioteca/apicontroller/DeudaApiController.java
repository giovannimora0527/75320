package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.DeudaApi;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.uniminuto.biblioteca.entity.Deuda;
import com.uniminuto.biblioteca.services.DeudaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


/**
 * Controlador REST para gestionar deudas.
 */
@RestController
public class DeudaApiController implements DeudaApi {

    @Autowired
    private DeudaService deudaService;

    @Override
    public ResponseEntity<List<Deuda>> listarDeudas() throws BadRequestException {
        return ResponseEntity.ok(this.deudaService.listarDeudas());
    }

    @Override
    public ResponseEntity<Deuda> obtenerDeuda(Integer id) throws BadRequestException {
        return ResponseEntity.ok(this.deudaService.obtenerDeudaPorId(id));
    }

    @Override
    public ResponseEntity<Deuda> crearDeuda(Deuda deuda) throws BadRequestException {
        return ResponseEntity.ok(this.deudaService.crearDeuda(deuda));
    }

    @Override
    public ResponseEntity<Deuda> actualizarDeuda(Integer id, Deuda deuda) throws BadRequestException {
        return ResponseEntity.ok(this.deudaService.actualizarDeuda(id, deuda));
    }

    @Override
    public ResponseEntity<Deuda> pagarDeuda(Integer id, String tipoPago) throws BadRequestException {
        return ResponseEntity.ok(this.deudaService.pagarDeuda(id, tipoPago));
    }
}
