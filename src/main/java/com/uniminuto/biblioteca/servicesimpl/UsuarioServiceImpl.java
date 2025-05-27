package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

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
     * Patrón para validar números de teléfono (solo dígitos).
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d+$");

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

    @Override
    @Transactional
    public List<Usuario> guardarUsuariosMasivo(List<UsuarioRq> usuarios) throws BadRequestException {
        if (usuarios == null || usuarios.isEmpty()) {
            throw new BadRequestException("La lista de usuarios no puede estar vacía");
        }

        // Validar duplicados en el archivo de entrada
        validarDuplicadosEnArchivo(usuarios);

        // Validar duplicados con la BD
        validarDuplicadosEnBD(usuarios);

        // Si llegamos aquí, no hay duplicados, procedemos a guardar
        List<Usuario> usuariosGuardados = new ArrayList<>();
        int fila = 1;

        for (UsuarioRq usuarioRq : usuarios) {
            try {
                // Validar campos requeridos
                if (usuarioRq.getNombre() == null || usuarioRq.getNombre().trim().isEmpty()) {
                    throw new BadRequestException("El nombre en la fila " + fila + " es requerido");
                }
                if (usuarioRq.getCorreo() == null || usuarioRq.getCorreo().trim().isEmpty()) {
                    throw new BadRequestException("El correo en la fila " + fila + " es requerido");
                }
                if (usuarioRq.getTelefono() == null || usuarioRq.getTelefono().trim().isEmpty()) {
                    throw new BadRequestException("El teléfono en la fila " + fila + " es requerido");
                }

                // Validar formato de correo
                if (!validarCorreo(usuarioRq.getCorreo())) {
                    throw new BadRequestException("El correo en la fila " + fila + " no es válido");
                }

                // Validar formato de teléfono
                if (!PHONE_PATTERN.matcher(usuarioRq.getTelefono()).matches()) {
                    throw new BadRequestException("El teléfono en la fila " + fila + " solo debe contener números");
                }

                // Convertir y guardar usuario
                Usuario usuario = convertirUsuarioRqToUsuario(usuarioRq);
                usuariosGuardados.add(usuarioRepository.save(usuario));

            } catch (BadRequestException e) {
                throw new BadRequestException("Error en la fila " + fila + ": " + e.getMessage());
            }
            fila++;
        }

        return usuariosGuardados;
    }

    /**
     * Valida que no existan duplicados en el archivo de entrada.
     * 
     * @param usuarios Lista de usuarios a validar
     * @throws BadRequestException si se encuentran duplicados
     */
    private void validarDuplicadosEnArchivo(List<UsuarioRq> usuarios) throws BadRequestException {
        // Validar nombres duplicados en el archivo
        for (int i = 0; i < usuarios.size(); i++) {
            for (int j = i + 1; j < usuarios.size(); j++) {
                UsuarioRq usuario1 = usuarios.get(i);
                UsuarioRq usuario2 = usuarios.get(j);

                if (usuario1.getNombre().equalsIgnoreCase(usuario2.getNombre())) {
                    throw new BadRequestException(
                            String.format("El nombre '%s' está duplicado en las filas %d y %d",
                                    usuario1.getNombre(), i + 1, j + 1));
                }

                if (usuario1.getCorreo().equalsIgnoreCase(usuario2.getCorreo())) {
                    throw new BadRequestException(
                            String.format("El correo '%s' está duplicado en las filas %d y %d",
                                    usuario1.getCorreo(), i + 1, j + 1));
                }
            }
        }
    }

    /**
     * Valida que no existan duplicados con registros en la base de datos.
     * 
     * @param usuarios Lista de usuarios a validar
     * @throws BadRequestException si se encuentran duplicados
     */
    private void validarDuplicadosEnBD(List<UsuarioRq> usuarios) throws BadRequestException {
        for (int i = 0; i < usuarios.size(); i++) {
            UsuarioRq usuario = usuarios.get(i);

            if (usuarioRepository.existsByNombre(usuario.getNombre())) {
                throw new BadRequestException(
                        String.format("El nombre '%s' en la fila %d ya existe en la base de datos",
                                usuario.getNombre(), i + 1));
            }

            if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
                throw new BadRequestException(
                        String.format("El correo '%s' en la fila %d ya existe en la base de datos",
                                usuario.getCorreo(), i + 1));
            }
        }
    }

}