package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.AutorApi;
import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lmora
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
        return ResponseEntity.ok(this.autorService.obtenerListadoAutores());
    }

    @Override
    public ResponseEntity<List<Autor>> listarAutoresByNacionalidad(String nacionalidad)
            throws BadRequestException {
        return ResponseEntity.ok(this.autorService
                .obtenerListadoAutoresPorNacionalidad(nacionalidad));
    }

    @Override
    public ResponseEntity<Autor> listarAutorPorId(Integer autorId) 
            throws BadRequestException {
        return ResponseEntity.ok(this.autorService.obtenerAutorPorId(autorId));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> crearAutor(AutorRq AutorRq) 
            throws BadRequestException {
        return ResponseEntity.ok(this.autorService.crearAutor(AutorRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarAutor(Autor autor) 
            throws BadRequestException {
        return ResponseEntity.ok(this.autorService.actualizarAutor(autor));
    }

}
