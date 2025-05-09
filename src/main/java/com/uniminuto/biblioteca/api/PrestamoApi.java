package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import java.time.LocalDate;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {
    @PostMapping("/crear")
    ResponseEntity<RespuestaGenerica> crearPrestamo(@RequestBody PrestamoRq prestamo) throws BadRequestException;

    @GetMapping(value = "/listar", produces = "application/json")
    ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException;

    @GetMapping(value = "/obtener-id", produces = "application/json")
    ResponseEntity<Prestamo> obtenerPrestamoPorId(@RequestParam Integer idPrestamo) throws BadRequestException;

    @GetMapping(value = "/por-usuario", produces = "application/json")
    ResponseEntity<List<Prestamo>> obtenerPorUsuario(@RequestParam String usuario) throws BadRequestException;

    @GetMapping(value = "/por-estado", produces = "application/json")
    ResponseEntity<List<Prestamo>> obtenerPorEstado(@RequestParam String estado) throws BadRequestException;

    @GetMapping(value = "/por-libro", produces = "application/json")
    ResponseEntity<List<Prestamo>> obtenerPorLibro(@RequestParam Integer idLibro) throws BadRequestException;

    @GetMapping(value = "/por-fechas", produces = "application/json")
    ResponseEntity<List<Prestamo>> obtenerPorFechas(@RequestParam String inicio, @RequestParam String fin) throws BadRequestException;
}
