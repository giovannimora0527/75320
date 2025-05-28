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

import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
    
    @Override
public UsuarioRs cargarUsuariosDesdeCsv(MultipartFile archivo) throws BadRequestException {
    if (archivo.isEmpty()) {
        throw new BadRequestException("El archivo CSV está vacío.");
    }

    String nombreArchivo = archivo.getOriginalFilename();
    if (nombreArchivo == null || !nombreArchivo.toLowerCase().endsWith(".csv")) {
        throw new BadRequestException("Solo se permiten archivos CSV");
    }

    int guardados = 0;
    int duplicados = 0;
    int errores = 0;
    StringBuilder erroresDetalle = new StringBuilder();

    // Patrón para validar nombre solo con letras y espacios
    Pattern NOMBRE_PATTERN = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$");

    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {

        String linea;
        int fila = 1;

        while ((linea = reader.readLine()) != null) {
            if (linea.startsWith("nombre,correo,telefono")) {
                fila++;
                continue;
            }

            String[] campos = linea.split(",");
            if (campos.length < 3) {
                erroresDetalle.append("Fila ").append(fila).append(": Datos incompletos.\n");
                errores++;
                fila++;
                continue;
            }

            String nombre = campos[0].trim();
            String correo = campos[1].trim();
            String telefono = campos[2].trim();

            // Validación de nombre vacío
            if (nombre.isBlank()) {
                erroresDetalle.append("Fila ").append(fila).append(": El nombre está vacío.\n");
                errores++;
                fila++;
                continue;
            }

            // Validación de nombre solo letras
            if (!NOMBRE_PATTERN.matcher(nombre).matches()) {
                erroresDetalle.append("Fila ").append(fila).append(": El nombre solo debe contener letras y espacios (sin números ni símbolos).\n");
                errores++;
                fila++;
                continue;
            }

            // Validación de correo
            if (!EMAIL_PATTERN.matcher(correo).matches()) {
                erroresDetalle.append("Fila ").append(fila).append(": El correo no es válido.\n");
                errores++;
                fila++;
                continue;
            }

            // Validación de teléfono
            if (!telefono.matches("\\d{7,15}")) {
                erroresDetalle.append("Fila ").append(fila).append(": El teléfono debe contener solo números (7 a 15 dígitos).\n");
                errores++;
                fila++;
                continue;
            }

            // Verificar duplicados
            if (usuarioRepository.existsByCorreo(correo) || usuarioRepository.existsByNombre(nombre)) {
                duplicados++;
                fila++;
                continue;
            }

            // Guardar usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setCorreo(correo);
            usuario.setTelefono(telefono);
            usuario.setActivo(true);
            usuario.setFechaRegistro(LocalDateTime.now());
            usuarioRepository.save(usuario);
            guardados++;
            fila++;
        }

    } catch (Exception e) {
        throw new BadRequestException("Error procesando el archivo CSV: " + e.getMessage());
    }

    UsuarioRs respuesta = new UsuarioRs();
    respuesta.setStatus(200);
    respuesta.setMessage("Usuarios guardados: " + guardados
            + ", duplicados ignorados: " + duplicados
            + ", errores: " + errores + "\n" + erroresDetalle.toString());

    return respuesta;
}



}
