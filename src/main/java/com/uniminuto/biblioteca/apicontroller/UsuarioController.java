package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/buscar")
    public Usuario buscarPorCorreo(@RequestParam String correo) {
        Optional<Usuario> usuario = usuarioService.buscarPorCorreo(correo);
        return usuario.orElseThrow(() -> new RuntimeException("Usuario no encontrado con el correo: " + correo));
    }

    @ExceptionHandler({IllegalArgumentException.class, RuntimeException.class})
    public Map<String, String> manejarExcepciones(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return error;
    }
}
