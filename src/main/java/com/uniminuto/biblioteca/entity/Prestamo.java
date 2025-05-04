package com.uniminuto.biblioteca.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libro;
    private String usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDate fechaEntrega;
    private String estado; // PRESTADO, DEVUELTO, VENCIDO

    // Constructores
    public Prestamo() {
    }

    public Prestamo(Long id, String libro, LocalDate fechaPrestamo, LocalDate fechaDevolucion, LocalDate fechaEntrega, String estado) {
        this.id = id;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }
    
    // Getters y Setters
public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getUsuario() {
    return usuario;
}

public void setUsuario(String usuario) {
    this.usuario = usuario;
}

public String getLibro() {
    return libro;
}

public void setLibro(String libro) {
    this.libro = libro;
}

public LocalDate getFechaPrestamo() {
    return fechaPrestamo;
}

public void setFechaPrestamo(LocalDate fechaPrestamo) {
    this.fechaPrestamo = fechaPrestamo;
}

public LocalDate getFechaDevolucion() {
    return fechaDevolucion;
}

public void setFechaDevolucion(LocalDate fechaDevolucion) {
    this.fechaDevolucion = fechaDevolucion;
}

public LocalDate getFechaEntrega() {
    return fechaEntrega;
}

public void setFechaEntrega(LocalDate fechaEntrega) {
    this.fechaEntrega = fechaEntrega;
}

public String getEstado() {
    return estado;
}

public void setEstado(String estado) {
    this.estado = estado;
}

   
}
