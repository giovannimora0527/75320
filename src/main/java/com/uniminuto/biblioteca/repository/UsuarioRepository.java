/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Usuario.
 * Esta interfaz extiende JpaRepository para proporcionar operaciones CRUD
 * básicas para la entidad Usuario. Incluye un método personalizado para
 * buscar un usuario por su correo electrónico.
 * 
 * @author Sofía Pedraza
 */
@Repository
public interface UsuarioRepository extends
        JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario dado un email.
     *
     * @param correo email de entrada.
     * @return Usuario que cumpla con el criterio.
     */
    Optional<Usuario> findByCorreo(String correo);

    /**
     * Encuentro un usuario por nombre.
     * @param nombre dato a buscar.
     * @return usuario que cumpla con la condicion.
     */    
    Optional<Usuario> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
    
    boolean existsByCorreo(String correo);

}