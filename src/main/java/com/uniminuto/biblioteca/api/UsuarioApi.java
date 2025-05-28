/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interfaz que define los métodos de la API para la gestión de usuarios.
 * Permite realizar operaciones como listar usuarios y obtener usuarios por correo.
 * 
  * @author Sofía Pedraza
 */
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public interface UsuarioApi {

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuarios()
            throws BadRequestException;

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param correo correo a buscar.
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/buscar-por-correo",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Usuario> buscarUsuarioPorEmail(
            @RequestParam String correo)
            throws BadRequestException;

    /**
     * Guarda un usuario nuevo.
     * @param usuario usuario a guardar.
     * @return respuesta del servicio.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/guardar-usuario",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<UsuarioRs> guardarUsuario(@RequestBody UsuarioRq usuario)
            throws BadRequestException;
    
    /**
     * Actualiza un usuario.
     * @param usuario usuario a actualizar.
     * @return respuesta del servicio.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/actualizar-usuario",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<UsuarioRs> actualizarUsuario(@RequestBody Usuario usuario)
            throws BadRequestException;

}