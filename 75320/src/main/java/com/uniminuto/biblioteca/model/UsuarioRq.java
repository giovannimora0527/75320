package com.uniminuto.biblioteca.model;
import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class UsuarioRq {
    /**
     * Id usuario.
     */
    private Integer idUsuario;
    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Correo electrónico del usuario (debe ser único).
     */    
    private String correo;

    /**
     * Número de teléfono del usuario (opcional).
     */   
    private String telefono;
}
