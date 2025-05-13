package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementacion del servicio para usuarios.
 *
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    /**
     * Patron para validar email.
     */
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    /**
     * Regex para validacion de email.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Repositorio de usuario.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodo() throws BadRequestException {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorCorreo(String correo) throws BadRequestException {
        Objects.requireNonNull(correo, "El correo es obligatorio");

        if (correo.isBlank() || !validarCorreo(correo)) {
            throw new BadRequestException("El correo proporcionado no es válido.");
        }

        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new BadRequestException("No hay registros de "
                + "usuarios con el correo ingresado."));
    }

    /**
     * Funcion para validar un correo.
     *
     * @param correo correo a validar.
     * @return si es valido o no.
     */
    private boolean validarCorreo(String correo) {
        return EMAIL_PATTERN.matcher(correo).matches();
    }

    @Override
    public UsuarioRs guardarUsuarioNuevo(UsuarioRq usuarioNuevo)
            throws BadRequestException {
     
        Optional<Usuario> optUser = this.usuarioRepository.findByNombre(usuarioNuevo.getNombre());
        if (optUser.isPresent()) {
            throw new BadRequestException("El nombre del usuario ya existe. Corrija e intente de nuevo.");
        }
        optUser = this.usuarioRepository.findByCorreo(usuarioNuevo.getCorreo());
        if (optUser.isPresent()) {
            throw new BadRequestException("El correo del usuario ya existe. Corrija e intente de nuevo.");
        }
        this.usuarioRepository.save(this.convertirUsuarioRqToUsuario(usuarioNuevo));
        UsuarioRs rta = new UsuarioRs();
        rta.setStatus(200);
        rta.setMessage("Se ha guardado el usuario satisfactoriamente");
        return rta;
    }

    private Usuario convertirUsuarioRqToUsuario(UsuarioRq usuarioNuevo) {
        Usuario usuario = new Usuario();
        usuario.setCorreo(usuarioNuevo.getCorreo());
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setNombre(usuarioNuevo.getNombre());
        usuario.setTelefono(usuarioNuevo.getTelefono());
        usuario.setActivo(true);
        return usuario;
    }

    @Override
    public UsuarioRs actualizarUsuario(Usuario usuario)
            throws BadRequestException {        
        UsuarioRs rta = new UsuarioRs();
        rta.setStatus(200);
        rta.setMessage("Se ha actualizado satisfactoriamente.");
        Optional<Usuario> optUserOrigin = this.usuarioRepository
                .findById(usuario.getIdUsuario());
        if (!cambioObjeto(optUserOrigin.get(), usuario)) {
            return rta;
        }
        // SI cambio datos.
        Usuario userActualizar = optUserOrigin.get();
        if (!usuario.getNombre().equals(userActualizar.getNombre())) {
            if (this.usuarioRepository.existsByNombre(usuario.getNombre())) {
               throw new BadRequestException("El usuario ya está registrado "
                       + "con el nombre " + usuario.getNombre());
            }
        }
        if (!usuario.getCorreo().equals(userActualizar.getCorreo())) {
            if (this.usuarioRepository.existsByCorreo(usuario.getCorreo())) {
                throw new BadRequestException("El usuario ya está registrado "
                       + "con el correo " + usuario.getCorreo());
            }
        }
        userActualizar.setNombre(usuario.getNombre());
        userActualizar.setCorreo(usuario.getCorreo());
        userActualizar.setTelefono(usuario.getTelefono());
        userActualizar.setActivo(usuario.getActivo());
        this.usuarioRepository.save(userActualizar);
        return rta;
    }

    private boolean cambioObjeto(Usuario userOrigin, Usuario usuarioFront) {
        if (!userOrigin.getNombre().equals(usuarioFront.getNombre())) {
            return true;
        }
        if (!userOrigin.getCorreo().equals(usuarioFront.getCorreo())) {
            return true;
        }
        if (!userOrigin.getTelefono().equals(usuarioFront.getTelefono())) {
            return true;
        }
        if (!userOrigin.getActivo().equals(usuarioFront.getActivo())) {
            return true;
        }
        return false;
    }

}
