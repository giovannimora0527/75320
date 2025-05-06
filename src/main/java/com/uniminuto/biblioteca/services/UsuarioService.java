/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;


/**
 *
 * @author nevar
 */

public interface UsuarioService {
    List<Usuario> obtenerListadoUsuarios();
    Usuario buscarPorEmail(String emailUsuario);
    Usuario guardarUsuario(Usuario usuario);
     Usuario actualizarUsuario(Usuario usuario);
}

