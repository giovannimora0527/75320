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
