
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.services.PrestamoService;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import java.util.List;
import lombok.Data;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Data
@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<Prestamo> listarPrestamoPorId(Integer prestamoId) throws BadRequestException {
        Prestamo prestamo = prestamoService.obtenerPorId(prestamoId)
                .orElseThrow(() -> new BadRequestException("No se encontró el préstamo con id: " + prestamoId));
        return ResponseEntity.ok(prestamo);
    }

    @Override
    public ResponseEntity<RespuestaGenerica> guardarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        // Validar que el ID del préstamo no sea nulo
    if (prestamoRq.getIdPrestamo() == null) {
        throw new BadRequestException("Debe proporcionar el ID del préstamo a actualizar.");
    }
    // Buscar préstamo existente por ID
    Prestamo prestamoExistente = prestamoService.obtenerPorId(prestamoRq.getIdPrestamo())
            .orElseThrow(() -> new BadRequestException("No se encontró el préstamo para actualizar."));

    // Solo actualizar la fecha de entrega
    prestamoExistente.setFechaEntrega(prestamoRq.getFechaEntrega());

    // Guardar solo el cambio de la fecha
    RespuestaGenerica respuesta = prestamoService.guardarPrestamo(prestamoExistente);
    return ResponseEntity.ok(respuesta);
}

    @Override
    public ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerListadoDePrestamos());
    }

    @Override
    public ResponseEntity<Prestamo> crearPrestamo(Prestamo prestamo) throws BadRequestException {
        Prestamo nuevoPrestamo = prestamoService.crearPrestamo(prestamo);
    return ResponseEntity.ok(nuevoPrestamo);
}
}
