package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Angie Vanegas
 */
@Data
@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;
    
    /**
     * Se define una relación de "muchos a uno" entre dos entidades
     */
    @ManyToOne
    /**
     * Especifica el nombre de la columna en la tabla prestamo que se usará como clave foránea (usuario_id, libro_id).
     */
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    private LocalDateTime fechaPrestamo;

    private LocalDateTime fechaEntrega;

    @Column(nullable = false)
    private LocalDateTime fechaDevolucion;
    
    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado = EstadoPrestamo.PRESTADO;
    
}
