
package com.uniminuto.biblioteca.enums;

/**
 *
 * @author Andres Peña
 */

/*
Se usa pera definir que la entidad prestamo en su atributo estado solo puede tener uno de alguno de los 3 tipos de valores
predefinidos, conjunto limitado y fijo de valores constantes.

Se usa cuando una variable solo puede tener un valor entre varios predefinidos
*/
public enum EstadoPrestamo {
    PRESTADO, VENCIDO, DEVUELTO
}
