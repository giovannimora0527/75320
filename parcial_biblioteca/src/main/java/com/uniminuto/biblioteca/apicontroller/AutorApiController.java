package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.AutorApi;
import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lmora
 */
@RestController
@RequestMapping("/api/autores") // Ruta base
public class AutorApiController implements AutorApi {
    /**
     * AutorService.
     */
    

    @Override
    public ResponseEntity<List<Autor>> listarAutores() throws BadRequestException {
       return ResponseEntity.ok(this.autorService.obtenerListadoAutores());
    }

    @Override
    public ResponseEntity<List<Autor>> listarAutoresByNacionalidad(String nacionalidad) 
            throws BadRequestException {
       return ResponseEntity.ok(this.autorService
               .obtenerListadoAutoresPorNacionalidad(nacionalidad));
    }

    @Override
    public ResponseEntity<Autor> listarAutorPorId(Integer autorId) throws BadRequestException {
       return ResponseEntity.ok(this.autorService.obtenerAutorPorId(autorId));
       
    }
    
    @Autowired
    private AutorService autorService;
    
    @GetMapping("/nacionalidad/{nacionalidad}")
    public ResponseEntity<List<Autor>> obtenerAutoresPorNacionalidad(@PathVariable String nacionalidad) {
        return ResponseEntity.ok(autorService.obtenerAutoresPorNacionalidad(nacionalidad));
    }
}
