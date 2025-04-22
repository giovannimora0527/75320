package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.CategoriaApi;
import com.uniminuto.biblioteca.entity.Categoria;
import com.uniminuto.biblioteca.services.CategoriaService;
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
public class CategoriaApiController implements CategoriaApi {

    @Autowired
    private CategoriaService categoriaService;

    @Override
    public ResponseEntity<List<Categoria>> listarCategorias() throws BadRequestException {
        return ResponseEntity.ok(this.categoriaService.obtenerListaCategorias());
    }

}
