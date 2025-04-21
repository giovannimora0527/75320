package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UserApi;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.service.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioApiController implements UserApi {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() throws BadRequestException {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @Override
    @GetMapping("/listar-usuario-id")
    public ResponseEntity<Usuario> listarUsuarioPorId(@RequestParam Integer usuarioId) throws BadRequestException {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(usuarioId));
    }

    @Override
    @PostMapping("/guardar-usuario")
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) throws BadRequestException {
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuario));
    }

    @Override
    @PutMapping("/actualizar-usuario")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) throws BadRequestException {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(usuario));
    }
}
