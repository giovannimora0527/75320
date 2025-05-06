/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
/**
 *
 * @author nevar
 */
@Data
@Entity
@Table (name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    
    @Column(name = "email_usuario")
    private String emailUsuario;
    
       @Column(name = "telefono_usuario")
    private String telefono;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    
      @Column(name = "estado") 
    private Boolean estado;
    
    }
