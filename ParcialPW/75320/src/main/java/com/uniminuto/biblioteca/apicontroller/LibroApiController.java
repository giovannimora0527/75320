package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.LibroApi;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.services.LibroService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author lmora
 */
@RestController
@RequestMapping("/libro")
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
    
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Libro> obtenerLibroPorTitulo(@PathVariable String titulo) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroPorTitulo(titulo));
    }
    
    // Nuevo endpoint para filtrar por rango de años
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Libro>> obtenerLibrosPorRangoDeFechas(
            @RequestParam Integer inicio,
            @RequestParam Integer fin) throws BadRequestException {
        return ResponseEntity.ok(libroService.obtenerLibrosPorRangoDeFechas(inicio, fin));
    }
}
