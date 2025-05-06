/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz de servicio para la entidad Usuario.
 * Esta interfaz define los métodos que deben ser implementados por cualquier clase
 * que gestione las operaciones relacionadas con los usuarios en el sistema,
 * como la obtención de usuarios por correo y la lista completa de usuarios.
 * 
 * @author Sofía Pedraza
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
     * 
     * @param usuario
     * @return
     * @throws BadRequestException 
     */
    UsuarioRs guardarUsuarioNuevo(UsuarioRq usuario) throws BadRequestException;
    
    /**
     * 
     * @param usuario
     * @return
     * @throws BadRequestException 
     */
    UsuarioRs actualizarUsuario(Usuario usuario) throws BadRequestException;
    
}