package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class LibroRs {
    /**
     * Respuesta del servicio.
     */
   private String message;
   /**
    * Status de la peticion. 
    */
   private Integer status;   
}
