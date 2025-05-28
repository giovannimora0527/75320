package com.uniminuto.biblioteca.model;

/**
 *
 * @author Andres Peña
 * 
 * Respuesta para errores en la carga de archivo csv
 */

public class CargaMasivaError {
    private int linea;
    private String mensaje;

    public CargaMasivaError(int linea, String mensaje) {
        this.linea = linea;
        this.mensaje = mensaje;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
