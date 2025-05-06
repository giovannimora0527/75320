package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/prestamo")
public interface PrestamoApi {

    @GetMapping("/listar-prestamo")
    ResponseEntity<List<Prestamo>> listar();

    @PostMapping("/crear-prestamo")
    ResponseEntity<Prestamo> crear(@RequestBody Prestamo prestamo);

    @PutMapping("/actualizar-entrega")
    ResponseEntity<Prestamo> actualizarEntrega(
            @RequestParam Long idPrestamo,
            @RequestParam String fechaEntrega // formato ISO: yyyy-MM-dd
    );
}
