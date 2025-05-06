package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de usuarios.
 * Esta clase contiene la lógica de negocio relacionada con los usuarios,
 * incluyendo la obtención de usuarios por correo electrónico y la validación
 * del formato del correo electrónico.
 * 
 * @author Sofía Pedraza
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
        if (correo == null || correo.isBlank()) {
            throw new BadRequestException("El correo: " + correo + ", no cumple "
                    + "la validación para ser un correo valido.");
        }

        boolean isValidoEmail = this.validarCorreo(correo);
        if (!isValidoEmail) {
            throw new BadRequestException("El correo no es valido.");
        }

        Optional<Usuario> optUsuario = this.usuarioRepository
                .findByCorreo(correo);
        if (!optUsuario.isPresent()) {
            throw new BadRequestException("No hay registros de usuarios "
                    + "registrados con el correo ingresado.");
        }
        return optUsuario.get();
    }

    /**
     *
     * @param correo
     * @return
     */
    public boolean validarCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(correo).matches();
    }

    @Override
    public UsuarioRs guardarUsuarioNuevo(UsuarioRq usuario) throws BadRequestException {
        Optional<Usuario> optUser = this.usuarioRepository
                .findByNombre(usuario.getNombre());
        if (optUser.isPresent()) {
            throw new BadRequestException("El usuario ya existe con el nombre "
                    + usuario.getNombre()
                    + ", Verifique e intente de nuevo.");
        }

        optUser = this.usuarioRepository
                .findByCorreo(usuario.getCorreo());

        if (optUser.isPresent()) {
            throw new BadRequestException("El usuario ya existe con el correo "
                    + usuario.getCorreo()
                    + ", Verifique e intente de nuevo.");
        }
        this.usuarioRepository.save(this.convertirUsuarioRqToUsuario(usuario));
        UsuarioRs rta = new UsuarioRs();
        rta.setMessage("Se ha guardado el usuario con exito.");
        return rta;
    }

    private Usuario convertirUsuarioRqToUsuario(UsuarioRq usuario) {
        Usuario user = new Usuario();
        user.setNombre(usuario.getNombre());
        user.setActivo(true);
        user.setFechaRegistro(LocalDateTime.now());
        user.setCorreo(usuario.getCorreo());
        user.setTelefono(usuario.getTelefono());
        return user;
    }

    @Override
    public UsuarioRs actualizarUsuario(Usuario usuario) throws BadRequestException {        
        Optional<Usuario> optUser = this.usuarioRepository.findById(usuario.getIdUsuario());
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario.");
        }
        UsuarioRs rta = new UsuarioRs();
        rta.setMessage("El usuario se actualizó correctamente.");
        Usuario userActual = optUser.get();
        if (!cambioObjeto(userActual, usuario)) {
            return rta;
        }

        if (!usuario.getNombre().equals(userActual.getNombre())) {
           if (this.usuarioRepository.existsByNombre(usuario.getNombre())) {
               throw new BadRequestException("El nombre del usuario: " + usuario.getNombre() 
                       + ", existe en la bd. Verifique e intente de nuevo.");
           }
        }
        if (!usuario.getCorreo().equals(userActual.getCorreo())) {
            if (this.usuarioRepository.existsByCorreo(usuario.getCorreo())) {
               throw new BadRequestException("El correo del usuario: " + usuario.getCorreo() 
                       + ", existe en la bd. Verifique e intente de nuevo.");
           }
        }
        userActual.setNombre(usuario.getNombre());
        userActual.setCorreo(usuario.getCorreo());
        userActual.setTelefono(usuario.getTelefono());
        userActual.setActivo(usuario.getActivo());
        this.usuarioRepository.save(userActual);
        return rta;
    }

    private boolean cambioObjeto(Usuario userActual, Usuario userFront) {
        if (!userActual.getNombre().equals(userFront.getNombre())
                || !userActual.getCorreo().equals(userFront.getCorreo())
                || !userActual.getTelefono().equals(userFront.getTelefono())
                || !userActual.getActivo().equals(userFront.getActivo())) {
            return true;
        }
        return false;
    }

}