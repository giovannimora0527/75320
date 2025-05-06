package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    @Override
    public ResponseEntity<List<Prestamo>> listar() {
        return ResponseEntity.ok(prestamoService.listarPrestamos());
    }

    @Override
    public ResponseEntity<Prestamo> crear(Prestamo prestamo) {
        return ResponseEntity.ok(prestamoService.crearPrestamo(prestamo));
    }

    @Override
    public ResponseEntity<Prestamo> actualizarEntrega(Long idPrestamo, String fechaEntrega) {
        LocalDate entrega = LocalDate.parse(fechaEntrega);
        return ResponseEntity.ok(prestamoService.actualizarEntrega(idPrestamo, entrega));
    }
}
