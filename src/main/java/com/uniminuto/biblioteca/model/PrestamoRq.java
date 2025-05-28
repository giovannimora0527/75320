<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.model;

import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author kano-
 */
@Data
public class PrestamoRq {
   private Integer usuarioId;
   private Integer libroId;
   private LocalDateTime fechaDevolucion;
}
=======
/**
 * 
 * @author Sofía Pedraza
 */
package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;

/**
 * Clase que representa la solicitud de préstamo de un libro.
 */
@Data
public class PrestamoRq {

    /**
     * Identificador del usuario que realiza el préstamo.
     */
    private Integer idUsuario;

    /**
     * Identificador del libro a prestar.
     */
    private Integer idLibro;


    /**
     * Fecha esperada para la devolución del libro.
     */
    private LocalDate fechaDevolucion;
}
>>>>>>> 5c56f8d446d5a59b870431403c8c05c636d466fd
