package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.LibroApi;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.LibroDisponibleProjection;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 * Controlador que implementa los métodos definidos en la interfaz LibroApi.
 * Proporciona los endpoints necesarios para gestionar libros, como listar
 * libros, obtener libros por ID, título o autor, y listar libros en un rango de
 * fechas.
 *
  * @author Sofía Pedraza
 */
@RestController
public class LibroApiController implements LibroApi {

    @Autowired
    private LibroService libroService;

    @Override
    public ResponseEntity<List<Libro>> listarLibros()
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService.listarLibros());
    }

    @Override
    public ResponseEntity<Libro> obtenerLibroPorId(Integer libroId)
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroId(libroId));
    }

    @Override
    public ResponseEntity<List<Libro>>
            obtenerLibroPorAutor(Integer autorId) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibrosPorAutor(autorId));
    }

    @Override
    public ResponseEntity<Libro> obtenerLibroPorNombre(String nombreLibro)
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroPorNombre(nombreLibro));
    }

    @Override
    public ResponseEntity<List<Libro>> obtenerLibroPorFechaPublicacion(
            Integer anioIni, Integer anioFin)
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService
                .obtenerLibroXRangoPublicacion(anioIni, anioFin));
    }

    @Override
    public ResponseEntity<List<LibroDisponibleProjection>> listarLibrosDisponibles()
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibrosDisponibles());
    }

}