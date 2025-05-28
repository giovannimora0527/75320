package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Autor;
import java.util.List;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * API para la gestión de autores
 */
@CrossOrigin(origins = "*")
@RequestMapping("/autor")
public interface AutorApi {

    /**
     * Lista todos los autores registrados.
     * 
     * @return lista de autores.
     * @throws BadRequestException si ocurre un error.
     */
    @RequestMapping(value = "/listar", 
                    method = RequestMethod.GET, 
                    produces = "application/json")
    ResponseEntity<List<AutorRs>> listarAutores() throws BadRequestException;

    /**
     * Guarda un nuevo autor.
     *
     * @param autor datos del autor a guardar.
     * @return respuesta de éxito o error.
     * @throws BadRequestException si ya existe un autor con ese nombre.
     */
    @RequestMapping(value = "/guardar", 
                    method = RequestMethod.POST, 
                    consumes = "application/json", 
                    produces = "application/json")
    ResponseEntity<RespuestaGenerica> guardarAutor(@RequestBody AutorRq autor) throws BadRequestException;

    /**
     * Actualiza un autor existente.
     *
     * @param autor datos del autor actualizado.
     * @return respuesta de éxito o error.
     * @throws BadRequestException si el nuevo nombre ya está en uso.
     */
    @RequestMapping(value = "/actualizar", 
                    method = RequestMethod.POST, 
                    consumes = "application/json", 
                    produces = "application/json")
    ResponseEntity<RespuestaGenerica> actualizarAutor(@RequestBody AutorRq autor) throws BadRequestException;
    
    
    @RequestMapping(value = "/cargar-autores",
        method = RequestMethod.POST,
        consumes = {"multipart/form-data"},
        produces = {"application/json"})
    ResponseEntity<?> cargarAutoresDesdeCsv(@RequestParam("file") MultipartFile file)
        throws BadRequestException;
}