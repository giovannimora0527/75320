/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.entity;

/**
 *
  * @author Sofía Pedraza
 */
public interface LibroDisponibleProjection {

    Long getIdLibro();

    String getTitulo();

    Integer getExistencias();

    Long getIdAutor();

    String getNombreAutor();

    String getNacionalidad();

    String getFechaNacimiento();

    Long getCategoriaId();

    String getNombreCategoria();

    Integer getPrestados();

    Integer getDisponibles();
}