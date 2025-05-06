/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador que implementa los métodos definidos en la interfaz UsuarioApi.
 * Proporciona los endpoints necesarios para gestionar usuarios, como listar usuarios
 * y obtener usuarios por correo electrónico.
 * 
 * @author Sofía Pedraza
 */
@RestController
public class UsuarioApiController implements UsuarioApi {

    /**
     * Servicio de usuarios.
     */
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.listarTodo());
    }

    @Override
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(String correo) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.buscarPorCorreo(correo));
    }

    @Override
    public ResponseEntity<UsuarioRs> guardarUsuario(UsuarioRq usuario) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.guardarUsuarioNuevo(usuario));
    }

    @Override
    public ResponseEntity<UsuarioRs> actualizarUsuario(Usuario usuario) throws BadRequestException {
      return ResponseEntity.ok(this.usuarioService.actualizarUsuario(usuario));
    }

}