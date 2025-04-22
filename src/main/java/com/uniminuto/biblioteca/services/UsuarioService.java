package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface UsuarioService {
    
    /**
     * Servicio para listar todos los usuarios del sistema.
     * @return Lista de usuarios registrados.
     * @throws BadRequestException Excepcion.
     */
    List<Usuario> listarTodo() throws BadRequestException;
    
    /**
     * Busca un usuario dado un email.
     * @param correo email a buscar.
     * @return Usuario.
     * @throws BadRequestException excepcion.
     */
    Usuario buscarPorCorreo(String correo) throws BadRequestException;
    
    /**
     * Guarda un usuario nuevo.
     * @return Respuesta del servicio.
     * @throws BadRequestException excepcion del servicio.
     */
    UsuarioRs guardarUsuarioNuevo(UsuarioRq usuarioNuevo) throws BadRequestException;
    
    
    /**
     * Actualiza un usuario.
     * @return Respuesta del servicio.
     * @throws BadRequestException excepcion del servicio.
     */
    UsuarioRs actualizarUsuario(Usuario usuario) throws BadRequestException;
    
}
