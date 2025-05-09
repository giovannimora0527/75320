package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.time.LocalDate;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    @Override
    public ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.listarPrestamos());
    }

    @Override
    public ResponseEntity<Prestamo> obtenerPrestamoPorId(Integer idPrestamo) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerPrestamoPorId(idPrestamo));
    }

    @Override
    public ResponseEntity<List<Prestamo>> obtenerPorUsuario(String usuario) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerPrestamosPorUsuario(usuario));
    }

    @Override
    public ResponseEntity<List<Prestamo>> obtenerPorEstado(String estado) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerPrestamosPorEstado(estado));
    }

    @Override
    public ResponseEntity<List<Prestamo>> obtenerPorLibro(Integer idLibro) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerPrestamosPorLibro(idLibro));
    }

    @Override
    public ResponseEntity<List<Prestamo>> obtenerPorFechas(String inicio, String fin) throws BadRequestException {
        LocalDate fechaIni = LocalDate.parse(inicio);
        LocalDate fechaFin = LocalDate.parse(fin);
        return ResponseEntity.ok(prestamoService.obtenerPrestamosPorRangoFecha(fechaIni, fechaFin));
    }

    @Override
    public ResponseEntity<RespuestaGenerica> crearPrestamo(@RequestBody PrestamoRq prestamo) throws BadRequestException {
        return new ResponseEntity<>(prestamoService.crearPrestamo(prestamo), HttpStatus.CREATED);
    }
}
