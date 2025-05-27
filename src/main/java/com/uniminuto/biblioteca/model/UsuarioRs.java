package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author juand
 */
@Data
public class UsuarioRs {
    /**
     * Respuesta del servicio.
     */
   private String message;
   /**
    * Status de la peticion. 
    */
   private Integer status;   
}