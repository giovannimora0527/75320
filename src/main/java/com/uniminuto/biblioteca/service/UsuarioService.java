package com.uniminuto.biblioteca.service;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long usuarioId) throws BadRequestException {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (!usuario.isPresent()) {
            throw new BadRequestException("El usuario no existe.");
        }
        return usuario.get();
    }

    public Usuario guardarUsuario(Usuario usuario) throws BadRequestException {
        Optional<Usuario> existingUsuario = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (existingUsuario.isPresent()) {
            throw new BadRequestException("El correo del usuario ya existe en el sistema.");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Usuario usuario) throws BadRequestException {
        if (usuario.getId() == null) {
            throw new BadRequestException("El ID del usuario es requerido para la actualización.");
        }
        Optional<Usuario> existingUsuario = usuarioRepository.findById(usuario.getId());
        if (!existingUsuario.isPresent()) {
            throw new BadRequestException("El usuario a actualizar no existe.");
        }
        Optional<Usuario> usuarioConCorreo = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (usuarioConCorreo.isPresent() && !usuarioConCorreo.get().getId().equals(usuario.getId())) {
            throw new BadRequestException("El correo del usuario ya existe en el sistema.");
        }
        return usuarioRepository.save(usuario);
    }
}
