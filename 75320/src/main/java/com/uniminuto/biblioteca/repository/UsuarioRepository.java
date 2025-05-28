package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author lmora
 */
public interface UsuarioRepository extends
        JpaRepository<Usuario, Integer> {
    
    /**
     * Busca un usuario dado un email.
     * @param correo email de entrada.
     * @return Usuario que cumpla con el criterio.
     */
    Optional<Usuario> findByCorreo(String correo);
    
    /**
     * Busca un usuario dado un nombre.
     * @param nombre nombre de entrada.
     * @return Usuario que cumpla con el criterio.
     */
    Optional<Usuario> findByNombre(String nombre);
    
    /**
     * Consulta si un usuario existe por nombre.
     * @param nombre dato de entrado.
     * @return si existe true/false.
     */
    boolean existsByNombre(String nombre);
    
    /**
     * Consulta si un usuario existe por correo.
     * @param correo dato de entrada.
     * @return si existe true/false.
     */
    boolean existsByCorreo(String correo);
    
}
