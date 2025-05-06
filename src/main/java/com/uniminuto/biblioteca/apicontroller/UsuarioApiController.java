/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nevar
 */
@RestController
public class UsuarioApiController implements UsuarioApi {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.obtenerListadoUsuarios());
    }

    @Override
    public ResponseEntity<Usuario> buscarUsuarioByEmail(String emailUsuario) throws BadRequestException {
        if (!isValidEmail(emailUsuario)) {
            System.out.println("El email no es valido");
            return ResponseEntity.badRequest().body(null);  // Retorna un 400 Bad Request si el email es inválido
        }
        Usuario usuario = this.usuarioService.buscarPorEmail(emailUsuario);

        if (usuario == null) {
            System.out.println("Usuario no existe");
            return ResponseEntity.notFound().build();  // Retorna un 404 si el usuario no se encuentra
        }
        System.out.println("El usuario es " + usuario.toString());
        return ResponseEntity.ok(usuario);
    }

    public boolean isValidEmail(String emailUsuario) {

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailUsuario);
        return matcher.matches();
    }
    
     @PostMapping("/guardar-usuario")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        usuario.setFechaRegistro(LocalDate.now());
        if (usuario.getEstado() == null) {
            usuario.setEstado(true);  // Si no se proporciona estado, lo dejamos como 'activo'
        }
        Usuario creado = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(creado);
    }

    @PostMapping("/actualizar-usuario")
    @Override
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) throws BadRequestException {
      if (usuario.getEstado() == null) {
            usuario.setEstado(true);  // Si no se proporciona estado, lo dejamos como 'activo'
        }
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }
    }
