
package com.uniminuto.biblioteca.model;
import java.time.LocalDateTime;
import lombok.Data;
/**
 *
 * @author Angie Vanegas Nieto
 */
@Data
public class PrestamoRq {
    //Segun requerimiento lo unico que se debe poder  editar es la fechaa dee entrega.
    private Integer idPrestamo;
    private LocalDateTime fechaEntrega;   
}
