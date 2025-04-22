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
 *
 * @author lmora
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
    public ResponseEntity<UsuarioRs> guardarUsuario(UsuarioRq usuarioNuevo) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.guardarUsuarioNuevo(usuarioNuevo));
    }

    @Override
    public ResponseEntity<UsuarioRs> actualizarUsuario(Usuario usuarioActualizar) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.actualizarUsuario(usuarioActualizar));
    }
    
}
