/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nevar
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> obtenerListadoUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorEmail(String emailUsuario) {
        return usuarioRepository.findByEmailUsuario(emailUsuario);
    }
//@Bean
//private UsuarioService usuario(){
//    return usuario();
//}
    
    }
  
    
