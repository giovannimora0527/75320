
package com.uniminuto.biblioteca.model;
import lombok.Data;
import java.time.LocalDate;

/**
 *
 * @author Andres Peña
 */

@Data
public class PrestamoEntregaRq {
    private Integer prestamoId;         
    private LocalDate fechaEntrega; 
}
