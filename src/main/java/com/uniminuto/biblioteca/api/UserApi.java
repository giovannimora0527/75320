package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

public interface UserApi {
    ResponseEntity<List<Usuario>> listarUsuarios() throws BadRequestException;

    ResponseEntity<Usuario> listarUsuarioPorId(Integer usuarioId) throws BadRequestException;

    ResponseEntity<Usuario> guardarUsuario(Usuario usuario) throws BadRequestException;

    ResponseEntity<Usuario> actualizarUsuario(Usuario usuario) throws BadRequestException;
}
