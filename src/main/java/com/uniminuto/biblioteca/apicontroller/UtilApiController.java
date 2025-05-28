package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UtilApi;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.services.NacionalidadService;
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
public class UtilApiController implements UtilApi {

    /**
     * Servicio de Nacionalidades.
     */
    @Autowired
    private NacionalidadService nacionalidadService;

    @Override
    public ResponseEntity<List<Nacionalidad>> listarNacionalidades() throws BadRequestException {
        return ResponseEntity.ok(this.nacionalidadService.obtenerListaNacionalidades());
    }
    
}
