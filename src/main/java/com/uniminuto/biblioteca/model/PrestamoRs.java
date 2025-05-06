
package com.uniminuto.biblioteca.model;

import lombok.Data;

@Data
/**
 *
 * @author Andres Peña
 */
public class PrestamoRs {
    private Integer id;
    private String nombreUsuario;       
    private String tituloLibro;         
    private String fechaPrestamo;       
    private String fechaDevolucion;    
    private String fechaEntrega;        
    private String estado;              
}
