package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Deuda;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface DeudaApi {

    @GetMapping
    ResponseEntity<List<Deuda>> listarDeudas() throws BadRequestException;

    @GetMapping("/{id}")
    ResponseEntity<Deuda> obtenerDeuda(@PathVariable Integer id) throws BadRequestException;

    @PostMapping
    ResponseEntity<Deuda> crearDeuda(@RequestBody Deuda deuda) throws BadRequestException;

    @PutMapping("/{id}")
    ResponseEntity<Deuda> actualizarDeuda(@PathVariable Integer id, @RequestBody Deuda deuda) throws BadRequestException;

    @PutMapping("/{id}/pagar")
    ResponseEntity<Deuda> pagarDeuda(@PathVariable Integer id, @RequestParam String tipoPago) throws BadRequestException;

}
