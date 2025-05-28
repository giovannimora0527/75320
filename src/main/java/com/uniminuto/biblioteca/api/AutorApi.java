package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/autor")
public interface AutorApi {
    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Autor>> listarAutores()
            throws BadRequestException;
    
     /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param nacionalidad nacionalidad del autor.
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-nacionalidad",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Autor>> listarAutoresByNacionalidad(
     @RequestParam String nacionalidad)
            throws BadRequestException;
    
     /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-autor-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Autor> listarAutorPorId(@RequestParam Integer autorIds)
            throws BadRequestException;
    
    @RequestMapping(value = "/guardar-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> crearAutor(@RequestBody AutorRq AutorRq)
            throws BadRequestException;

    @RequestMapping(value = "/actualizar-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> actualizarAutor(@RequestBody Autor autor)
            throws BadRequestException;
    
    @PostMapping(
    value = "/cargar-autores",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
ResponseEntity<AutorRs> cargarUsuariosDesdeCsv(@RequestParam("file") MultipartFile archivo) 
    throws BadRequestException;
    
}
