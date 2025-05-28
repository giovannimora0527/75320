package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class RespuestaGenerica {
    private String message;
    public RespuestaGenerica() {}

    public RespuestaGenerica(String message) {
        this.message = message;
    }
}
