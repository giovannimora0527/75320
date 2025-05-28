package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.AutorApi;
import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.model.CargaMasivaError;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador de la API para autores.
 */

@RestController
public class AutorApiController implements AutorApi {
   
    @Autowired
    private AutorService autorService;

    @Override
    public ResponseEntity<List<AutorRs>> listarAutores() throws BadRequestException {
        return ResponseEntity.ok(this.autorService.listarAutores());
    }

    @Override
    public ResponseEntity<RespuestaGenerica> guardarAutor(AutorRq autor) throws BadRequestException {
        return ResponseEntity.ok(this.autorService.guardarAutor(autor));
    }

    @Override
    public ResponseEntity<RespuestaGenerica> actualizarAutor(AutorRq autor) throws BadRequestException {
        return ResponseEntity.ok(this.autorService.actualizarAutor(autor));
    }
    
    @Override
    public ResponseEntity<?> cargarAutoresDesdeCsv(MultipartFile file) throws BadRequestException {
        List<CargaMasivaError> errores = autorService.cargarAutoresDesdeCsv(file);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(new RespuestaGenerica("Autores cargados correctamente"));
    }
    
}
