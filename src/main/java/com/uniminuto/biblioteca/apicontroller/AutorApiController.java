package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.AutorApi;
import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.service.AutorService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author lmora
 */
@RestController
@RequestMapping("/autor") // Changed base path to match frontend
public class AutorApiController implements AutorApi {
    /**
     * AutorService.
     */
    
    @Autowired
    private AutorService autorService;

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<Autor>> listarAutores() throws BadRequestException {
       return ResponseEntity.ok(this.autorService.listarAutores());
    }

    @Override
    @GetMapping("/listar-nacionalidad")
    public ResponseEntity<List<Autor>> listarAutoresByNacionalidad(@RequestParam String nacionalidad) 
            throws BadRequestException {
       return ResponseEntity.ok(this.autorService.obtenerListadoAutoresPorNacionalidad(nacionalidad));
    }

    @Override
    @GetMapping("/listar-autor-id")
    public ResponseEntity<Autor> listarAutorPorId(@RequestParam Integer autorId) throws BadRequestException {
       return ResponseEntity.ok(this.autorService.obtenerAutorPorId(autorId));
    }

    @Override
    @PostMapping("/guardar-autor")
    public ResponseEntity<Autor> guardarAutor(@RequestBody Autor autor) throws BadRequestException {
        return ResponseEntity.ok(this.autorService.guardarAutor(autor));
    }

    @Override
    @PutMapping("/actualizar-autor")
    public ResponseEntity<Autor> actualizarAutor(@RequestBody Autor autor) throws BadRequestException {
        return ResponseEntity.ok(this.autorService.actualizarAutor(autor));
    }
}
