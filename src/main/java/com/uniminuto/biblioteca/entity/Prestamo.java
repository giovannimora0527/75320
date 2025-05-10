package com.uniminuto.biblioteca.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "prestamos")
@Data
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @Column(name = "fecha_prestamo")
    private LocalDateTime fechaPrestamo;
    
    @Column(name = "fecha_devolucion")
    private LocalDateTime fechaDevolucion;
    
    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    private String Estado; // PRESTADO, DEVUELTO, VENCIDO

    // Constructores
    public Prestamo() {
    }
}
