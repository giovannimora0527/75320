package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.AutorApi;
import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
  * @author Sofía Pedraza
 */
@RestController
public class AutorApiController implements AutorApi {
    /**
     * AutorService.
     */
 @Autowired
    private AutorService autorService;

    @Override
    public ResponseEntity<List<Autor>> listarAutores() throws BadRequestException {
        return ResponseEntity.ok(autorService.listarTodo());
    }

    @Override
    public ResponseEntity<Autor> buscarAutorPorNombre(String nombre) throws BadRequestException {
        return ResponseEntity.ok(autorService.buscarPorNombre(nombre));
    }

    @Override
    public ResponseEntity<AutorRs> guardarAutor(AutorRq autor) throws BadRequestException {
        System.out.print(autor);
        return ResponseEntity.ok(autorService.guardarAutorNuevo(autor));
    }

    @Override
    public ResponseEntity<AutorRs> actualizarAutor(Autor autor) throws BadRequestException {
        return ResponseEntity.ok(autorService.actualizarAutor(autor));
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

    
}