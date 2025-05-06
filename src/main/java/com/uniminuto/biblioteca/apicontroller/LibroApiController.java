package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.LibroApi;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.services.LibroService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author lmora
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
    public ResponseEntity<Libro> obtenerLibroPorId(Integer libroId) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroId(libroId));
    }

    @Override
    public ResponseEntity<List<Libro>> listarLibroAutorId(Integer autorId) throws BadRequestException {
        if (autorId <= 0 || autorId.equals("")) {
            System.out.println("El ID del autor no puede ser nulo.");
            return ResponseEntity.badRequest().body(null);
        }

        List<Libro> libros = this.libroService.listarLibroAutorId(autorId);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros para el autor con ID:" + autorId);
            return ResponseEntity.notFound().build();
        }

//        return libros;
        return ResponseEntity.ok(this.libroService.listarLibroAutorId(autorId));
    }

    @Override
    public ResponseEntity<Libro> buscarNombreLibro(String titulo) throws BadRequestException {
        Libro libro = this.libroService.buscarNombreLibro(titulo);
        if (libro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(libro);
    }

    @Override
    public ResponseEntity<List<Libro>> listarLibrosRangoFechas(Integer fechaInicio, Integer fechaFin) throws BadRequestException {
        System.out.println("fallo " + fechaInicio);
        List<Libro> libros = libroService.listarLibrosRangoFechas(fechaInicio, fechaFin);

        if (libros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(libros);
    }

}
