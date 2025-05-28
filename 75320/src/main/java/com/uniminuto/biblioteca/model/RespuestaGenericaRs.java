package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class RespuestaGenericaRs {
    /**
     * Status del servicio.
     */
    private Integer status;
    /**
     * Mensaje del servicio.
     */
    private String message;
}
