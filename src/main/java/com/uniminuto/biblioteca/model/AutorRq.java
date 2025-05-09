/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author Sofía Pedraza
 */
@Data
public class AutorRq {
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
}